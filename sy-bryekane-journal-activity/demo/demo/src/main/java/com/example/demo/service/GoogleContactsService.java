package com.example.demo.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
@Service
public class GoogleContactsService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    public GoogleContactsService(OAuth2AuthorizedClientService oAuth2AuthorizedClientService){
        this.oAuth2AuthorizedClientService=oAuth2AuthorizedClientService;
    }
    public List<Person> gitcontacts(OAuth2AuthenticationToken token) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service =
                new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(token))
                        .setApplicationName("Google Contacts")
                        .build();

        // Request 10 connections.
        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(10)
                .setPersonFields("names,emailAddresses")
                .execute();

        // Print display name of connections if available.
        List<Person> connections = response.getConnections();
        if (connections != null && connections.size() > 0) {
            for (Person person : connections) {
                List<Name> names = person.getNames();
                if (names != null && names.size() > 0) {
                    System.out.println("Name: " + person.getNames().get(0)
                            .getDisplayName());
                } else {
                    System.out.println("No names available for connection.");
                }
            }
        } else {
            System.out.println("No connections found.");
        }

        return connections;
    }

    private HttpRequestInitializer getCredentials(OAuth2AuthenticationToken token) {
        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService.loadAuthorizedClient("google",token.getName());
        OAuth2AccessToken accessToken = client.getAccessToken();
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());
        return credential;
    }
}
