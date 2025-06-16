package com.ambos.googlecontacts;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class GooglecontactsApplication {

	private static final String APPLICATION_NAME = "Google People API Java Quickstart";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final List<String> SCOPES = Arrays.asList(PeopleServiceScopes.CONTACTS_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GooglecontactsApplication.class, args);
		fetchContacts();
	}

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		InputStream in = GooglecontactsApplication.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets =
				GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
				.setAccessType("offline")
				.build();

		return new com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp(
				flow, new com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver())
				.authorize("user");
	}

	private static void fetchContacts() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME)
				.build();

		ListConnectionsResponse response = service.people().connections()
				.list("people/me")
				.setPageSize(10)
				.setPersonFields("names,emailAddresses,phoneNumbers")
				.execute();

		List<Person> connections = response.getConnections();
		if (connections != null && !connections.isEmpty()) {
			for (Person person : connections) {
				List<Name> names = person.getNames();
				if (names != null && !names.isEmpty()) {
					System.out.println("Name: " + names.get(0).getDisplayName());
				} else {
					System.out.println("No names available for connection.");
				}
			}
		} else {
			System.out.println("No connections found.");
		}
	}
}
