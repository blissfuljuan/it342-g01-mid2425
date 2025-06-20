package com.estopace.google.contacts.service;

import com.estopace.google.contacts.model.Contact;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleContactsService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    public List<Contact> getContacts(OAuth2AuthenticationToken token) {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("google", token.getName());

            if (client == null) {
                throw new RuntimeException("OAuth2 client not found");
            }

            OAuth2AccessToken accessToken = client.getAccessToken();
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());

            PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName("Google Contacts")
                    .build();

            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return parseContacts(response.getConnections());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Contact> parseContacts(List<Person> persons) {
        List<Contact> contactsList = new ArrayList<>();

        if (persons != null) {
            for (Person person : persons) {
                String name = "No Name";
                List<String> emails = new ArrayList<>();
                List<String> phones = new ArrayList<>();

                if (person.getNames() != null && !person.getNames().isEmpty()) {
                    Name personName = person.getNames().get(0);
                    name = personName.getDisplayName();
                }

                if (person.getEmailAddresses() != null) {
                    for (EmailAddress email : person.getEmailAddresses()) {
                        emails.add(email.getValue());
                    }
                }

                if (person.getPhoneNumbers() != null) {
                    for (PhoneNumber phone : person.getPhoneNumbers()) {
                        phones.add(phone.getValue());
                    }
                }

                Contact contact = new Contact(name);
                contact.setEmails(emails);
                contact.setPhones(phones);
                contactsList.add(contact);
            }
        }

        return contactsList;
    }
}