package com.rigodon.google.contacts.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.*;
import com.rigodon.google.contacts.model.Contact;
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
    private static PeopleService peopleService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    private void initService(OAuth2AuthenticationToken token) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient("google", token.getName());
        if (client == null) throw new RuntimeException("OAuth2 client not found");
        OAuth2AccessToken accessToken = client.getAccessToken();
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());
        peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("Google Contacts Integration")
                .build();
    }

    public List<Contact> getContacts(OAuth2AuthenticationToken token) {
        try {
            initService(token);
            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();
            return parseContacts(response.getConnections());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to fetch contacts", e);
        }
    }

    private List<Contact> parseContacts(List<Person> persons) {
        List<Contact> contactsList = new ArrayList<>();
        if (persons == null) return contactsList;
        for (Person person : persons) {
            String name = (person.getNames() != null && !person.getNames().isEmpty())
                    ? person.getNames().get(0).getDisplayName()
                    : "No Name";
            List<String> emails = new ArrayList<>();
            if (person.getEmailAddresses() != null) {
                person.getEmailAddresses().forEach(email -> emails.add(email.getValue()));
            }
            List<String> phones = new ArrayList<>();
            if (person.getPhoneNumbers() != null) {
                person.getPhoneNumbers().forEach(phone -> phones.add(phone.getValue()));
            }
            Contact contact = new Contact(name);
            contact.setEmails(emails);
            contact.setPhones(phones);
            contact.setResourceName(person.getResourceName());
            contactsList.add(contact);
        }
        return contactsList;
    }

    public void addContact(OAuth2AuthenticationToken token, String name, String email, String phone) {
        try {
            initService(token);
            Person person = new Person();
            person.setNames(List.of(new Name().setDisplayName(name)));
            person.setEmailAddresses(List.of(new EmailAddress().setValue(email)));
            person.setPhoneNumbers(List.of(new PhoneNumber().setValue(phone)));
            peopleService.people().createContact(person).execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to add contact", e);
        }
    }

    public void updateContact(OAuth2AuthenticationToken token, String resourceName, String name, String email, String phone) {
        try {
            initService(token);
            Person person = new Person();
            person.setNames(List.of(new Name().setDisplayName(name)));
            person.setEmailAddresses(List.of(new EmailAddress().setValue(email)));
            person.setPhoneNumbers(List.of(new PhoneNumber().setValue(phone)));
            peopleService.people().updateContact(resourceName, person)
                    .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                    .execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to update contact", e);
        }
    }

    public void deleteContact(OAuth2AuthenticationToken token, String resourceName) {
        try {
            initService(token);
            peopleService.people().deleteContact(resourceName).execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to delete contact", e);
        }
    }
}
