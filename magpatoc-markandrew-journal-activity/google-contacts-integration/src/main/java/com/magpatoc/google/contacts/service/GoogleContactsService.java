package com.magpatoc.google.contacts.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.*;
import com.magpatoc.google.contacts.model.Contact;
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

            OAuth2AuthorizedClient client = authorizedClientService
                    .loadAuthorizedClient("google", token.getName());

            if (client == null) {
                throw new RuntimeException("OAuth2 client not found");
            }

            OAuth2AccessToken accessToken = client.getAccessToken();
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());

            PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName("Google Contacts Integration")
                    .build();

            ListConnectionsResponse response = peopleService.people().connections().list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return parseContacts(response.getConnections());

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to retrieve contacts", e);
        }
    }

    private List<Contact> parseContacts(List<Person> persons) {
        List<Contact> contactsList = new ArrayList<>();

        if (persons != null) {
            for (Person person : persons) {
                String name = extractName(person);
                List<String> emails = extractEmails(person);
                List<String> phones = extractPhones(person);

                Contact contact = new Contact(name);
                contact.setEmails(emails);
                contact.setPhones(phones);

                contactsList.add(contact);
            }
        }

        return contactsList;
    }

    private String extractName(Person person) {
        List<Name> names = person.getNames();
        return (names != null && !names.isEmpty()) ? names.get(0).getDisplayName() : "No Name";
    }

    private List<String> extractEmails(Person person) {
        List<String> emailsList = new ArrayList<>();
        List<EmailAddress> emails = person.getEmailAddresses();
        if (emails != null) {
            for (EmailAddress email : emails) {
                emailsList.add(email.getValue());
            }
        }
        return emailsList;
    }

    private List<String> extractPhones(Person person) {
        List<String> phonesList = new ArrayList<>();
        List<PhoneNumber> phones = person.getPhoneNumbers();
        if (phones != null) {
            for (PhoneNumber phone : phones) {
                phonesList.add(phone.getValue());
            }
        }
        return phonesList;
    }
}
