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
import java.util.stream.Collectors;

@Service
public class GoogleContactsService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    private PeopleService buildPeopleService(OAuth2AuthenticationToken token)
            throws GeneralSecurityException, IOException {

        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient("google", token.getName());

        if (client == null) {
            throw new RuntimeException("OAuth2 client not found");
        }

        OAuth2AccessToken accessToken = client.getAccessToken();
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());

        return new PeopleService.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName("Google Contacts Integration")
                .build();
    }

    public List<Contact> getContacts(OAuth2AuthenticationToken token) {
        try {
            PeopleService peopleService = buildPeopleService(token);

            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return parseContacts(response.getConnections());

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to retrieve contacts", e);
        }
    }

    public void addContact(OAuth2AuthenticationToken token, Contact contact) {
        try {
            PeopleService peopleService = buildPeopleService(token);

            Person person = new Person();

            if (contact.getName() != null && !contact.getName().isBlank()) {
                person.setNames(List.of(new Name().setGivenName(contact.getName())));
            }

            if (contact.getEmails() != null && !contact.getEmails().isEmpty()) {
                person.setEmailAddresses(contact.getEmails().stream()
                        .filter(email -> email != null && !email.isBlank())
                        .map(email -> new EmailAddress().setValue(email))
                        .collect(Collectors.toList()));
            }

            if (contact.getPhones() != null && !contact.getPhones().isEmpty()) {
                person.setPhoneNumbers(contact.getPhones().stream()
                        .filter(phone -> phone != null && !phone.isBlank())
                        .map(phone -> new PhoneNumber().setValue(phone))
                        .collect(Collectors.toList()));
            }

            peopleService.people().createContact(person).execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to add contact", e);
        }
    }

    public void updateContact(OAuth2AuthenticationToken token, Contact contact) {
        try {
            PeopleService peopleService = buildPeopleService(token);

            // Get existing contact (needed for ETag)
            Person existingPerson = peopleService.people()
                    .get(contact.getId())
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            Person updatedPerson = new Person();
            updatedPerson.setEtag(existingPerson.getEtag()); // Required for update

            if (contact.getName() != null && !contact.getName().isBlank()) {
                updatedPerson.setNames(List.of(new Name().setGivenName(contact.getName())));
            }

            if (contact.getEmails() != null && !contact.getEmails().isEmpty()) {
                updatedPerson.setEmailAddresses(contact.getEmails().stream()
                        .filter(email -> email != null && !email.isBlank())
                        .map(email -> new EmailAddress().setValue(email))
                        .collect(Collectors.toList()));
            }

            if (contact.getPhones() != null && !contact.getPhones().isEmpty()) {
                updatedPerson.setPhoneNumbers(contact.getPhones().stream()
                        .filter(phone -> phone != null && !phone.isBlank())
                        .map(phone -> new PhoneNumber().setValue(phone))
                        .collect(Collectors.toList()));
            }

            peopleService.people()
                    .updateContact(contact.getId(), updatedPerson)
                    .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to update contact", e);
        }
    }

    public void deleteContact(OAuth2AuthenticationToken token, String resourceName) {
        try {
            if (resourceName == null || !resourceName.startsWith("people/")) {
                throw new IllegalArgumentException("Invalid resource name: " + resourceName);
            }

            PeopleService peopleService = buildPeopleService(token);
            peopleService.people().deleteContact(resourceName).execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to delete contact", e);
        }
    }

    private List<Contact> parseContacts(List<Person> people) {
        List<Contact> contactsList = new ArrayList<>();

        if (people != null) {
            for (Person person : people) {
                String name = extractName(person);
                List<String> emails = extractEmails(person);
                List<String> phones = extractPhones(person);
                String resourceName = person.getResourceName();

                Contact contact = new Contact();
                contact.setId(resourceName);
                contact.setName(name);
                contact.setEmails(emails);
                contact.setPhones(phones);

                contactsList.add(contact);
            }
        }

        return contactsList;
    }

    private String extractName(Person person) {
        List<Name> names = person.getNames();
        return (names != null && !names.isEmpty()) ? names.get(0).getDisplayName() : "Unnamed";
    }

    private List<String> extractEmails(Person person) {
        List<String> result = new ArrayList<>();
        List<EmailAddress> emails = person.getEmailAddresses();
        if (emails != null) {
            for (EmailAddress email : emails) {
                result.add(email.getValue());
            }
        }
        return result;
    }

    private List<String> extractPhones(Person person) {
        List<String> result = new ArrayList<>();
        List<PhoneNumber> phones = person.getPhoneNumbers();
        if (phones != null) {
            for (PhoneNumber phone : phones) {
                result.add(phone.getValue());
            }
        }
        return result;
    }
}
