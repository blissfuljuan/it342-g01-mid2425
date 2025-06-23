package com.mandawe.google.contacts.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.*;
import com.mandawe.google.contacts.model.Contact;
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
    private static PeopleService peopleService;

    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    private void initializePeopleService(OAuth2AuthenticationToken token) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("google", token.getName());

        if (client == null) {
            throw new RuntimeException("OAuth2 client not found");
        }

        OAuth2AccessToken accessToken = client.getAccessToken();
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());

        peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("Google Contacts Integration")
                .build();
    }

    public List<Contact> getContacts(OAuth2AuthenticationToken token) {
        try {
            initializePeopleService(token);

            ListConnectionsResponse response = peopleService.people().connections().list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return parseContact(response);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to get contacts", e);
        }
    }

    private List<Contact> parseContact(ListConnectionsResponse response) {
        List<Contact> contactsList = new ArrayList<>();
        List<Person> persons = response.getConnections();

        if (response != null && persons != null) {
            for (Person person : persons) {
                String name = (person.getNames() != null && !person.getNames().isEmpty())
                        ? person.getNames().get(0).getDisplayName()
                        : "No Name";

                List<String> emails = new ArrayList<>();
                if (person.getEmailAddresses() != null) {
                    for (EmailAddress email : person.getEmailAddresses()) {
                        emails.add(email.getValue());
                    }
                }

                List<String> phones = new ArrayList<>();
                if (person.getPhoneNumbers() != null) {
                    for (PhoneNumber phone : person.getPhoneNumbers()) {
                        phones.add(phone.getValue());
                    }
                }

                Contact contact = new Contact(name);
                contact.setEmails(emails);
                contact.setPhones(phones);
                contact.setResourceName(person.getResourceName());

                contactsList.add(contact);
            }
        }

        return contactsList;
    }

    public void addContact(OAuth2AuthenticationToken token, String name, String email, String phone) {
        try {
            initializePeopleService(token);

            Person person = new Person();
            person.setNames(List.of(new Name().setGivenName(name)));
            person.setEmailAddresses(List.of(new EmailAddress().setValue(email)));
            person.setPhoneNumbers(List.of(new PhoneNumber().setValue(phone)));

            peopleService.people().createContact(person).execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to add contact", e);
        }
    }

    public void updateContact(OAuth2AuthenticationToken token, String resourceName, String name, List<String> emails, List<String> phones, String etag) {
        try {
            initializePeopleService(token);

            Person updatedPerson = new Person();
            updatedPerson.setEtag(etag); // Required for updates
            updatedPerson.setNames(List.of(new Name().setGivenName(name)));

            List<EmailAddress> emailList = new ArrayList<>();
            for (String email : emails) {
                if (email != null && !email.isBlank()) {
                    emailList.add(new EmailAddress().setValue(email));
                }
            }
            updatedPerson.setEmailAddresses(emailList);

            List<PhoneNumber> phoneList = new ArrayList<>();
            for (String phone : phones) {
                if (phone != null && !phone.isBlank()) {
                    phoneList.add(new PhoneNumber().setValue(phone));
                }
            }
            updatedPerson.setPhoneNumbers(phoneList);

            peopleService.people().updateContact(resourceName, updatedPerson)
                    .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to update contact", e);
        }
    }

    public void deleteContact(OAuth2AuthenticationToken token, String resourceName) {
        try {
            initializePeopleService(token);
            peopleService.people().deleteContact(resourceName).execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to delete contact", e);
        }
    }

    public Contact getContactDetails(OAuth2AuthenticationToken token, String resourceName) {
        try {
            initializePeopleService(token);

            Person person = peopleService.people().get(resourceName)
                    .setPersonFields("names,emailAddresses,phoneNumbers,metadata")
                    .execute();

            String name = (person.getNames() != null && !person.getNames().isEmpty())
                    ? person.getNames().get(0).getDisplayName()
                    : "";

            List<String> emails = new ArrayList<>();
            if (person.getEmailAddresses() != null) {
                for (EmailAddress email : person.getEmailAddresses()) {
                    emails.add(email.getValue());
                }
            }

            List<String> phones = new ArrayList<>();
            if (person.getPhoneNumbers() != null) {
                for (PhoneNumber phone : person.getPhoneNumbers()) {
                    phones.add(phone.getValue());
                }
            }

            Contact contact = new Contact(name);
            contact.setEmails(emails);
            contact.setPhones(phones);
            contact.setResourceName(resourceName);
            contact.setEtag(person.getEtag()); // Add etag for update form

            return contact;

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to get contact details", e);
        }
    }
}
