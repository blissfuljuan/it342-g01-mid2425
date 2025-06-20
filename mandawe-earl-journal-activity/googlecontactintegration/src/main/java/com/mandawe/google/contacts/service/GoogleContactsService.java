package com.mandawe.google.contacts.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.PhoneNumber;
import com.google.api.services.people.v1.model.Name;
import com.mandawe.google.contacts.model.Contact;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoogleContactsService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final OAuth2AuthorizedClientService authorizedClientService;

    private static PeopleService peopleService;

    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) throws GeneralSecurityException, IOException {
        this.authorizedClientService = authorizedClientService;
    }

    public List getContacts(OAuth2AuthenticationToken token) {
        ListConnectionsResponse response = null;
        System.out.println("Token: " + token);
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            OAuth2AuthorizedClient client = authorizedClientService
                    .loadAuthorizedClient("google", token.getName());

            if (client == null) {
                throw new RuntimeException("OAuth2 client not found");
            }

            OAuth2AccessToken accessToken = client.getAccessToken();
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());
            peopleService = new PeopleService.Builder(HTTP_TRANSPORT,JSON_FACTORY,credential).build();

            response = peopleService.people().connections().list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return parseContact(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Contact> parseContact(ListConnectionsResponse response){
        List<Contact> contactsList = new ArrayList<>();
        List<Person> persons = response.getConnections();

        if (persons != null) {
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
                contactsList.add(contact);
            }
        }
        return contactsList;
    }

    private String extractName(Map<String, Object> person) {
        List<Map<String, Object>> names = (List<Map<String, Object>>) person.get("names");
        return (names != null && !names.isEmpty()) ? (String) names.get(0).get("displayName") : "No Name";
    }

    private List<String> extractEmails(Map<String, Object> person) {
        List<String> emailsList = new ArrayList<>();
        List<Map<String, Object>> emails = (List<Map<String, Object>>) person.get("emailAddresses");
        if (emails != null) {
            for (Map<String, Object> email : emails) {
                emailsList.add((String) email.get("value"));
            }
        }
        return emailsList;
    }

    private List<String> extractPhones(Map<String, Object> person) {
        List<String> emailsList = new ArrayList<>();
        List<Map<String, Object>> phones = (List<Map<String, Object>>) person.get("phoneNumbers");
        if (phones != null) {
            for (Map<String, Object> phone : phones) {
                emailsList.add((String) phone.get("value"));
            }
        }
        return emailsList;
    }

    public void addContact(OAuth2AuthenticationToken token, String name, String email, String phone) {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            OAuth2AuthorizedClient client = authorizedClientService
                    .loadAuthorizedClient("google", token.getName());

            if (client == null) {
                throw new RuntimeException("OAuth2 client not found");
            }

            OAuth2AccessToken accessToken = client.getAccessToken();
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());
            peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

            // Create new person
            Person person = new Person();

            // Set name
            Name personName = new Name();
            personName.setGivenName(name);
            person.setNames(List.of(personName));

            // Set email
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.setValue(email);
            person.setEmailAddresses(List.of(emailAddress));

            // Set phone
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setValue(phone);
            person.setPhoneNumbers(List.of(phoneNumber));

            // Create the contact
            peopleService.people().createContact(person).execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to add contact", e);
        }
    }
    public void updateContact(OAuth2AuthenticationToken token, String resourceName, String name, List<String> emails, List<String> phones) {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("google", token.getName());

            if (client == null) throw new RuntimeException("OAuth2 client not found");

            OAuth2AccessToken accessToken = client.getAccessToken();
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());
            peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

            Person person = new Person();
            person.setNames(List.of(new Name().setGivenName(name)));

            if (emails != null && !emails.isEmpty()) {
                List<EmailAddress> emailList = emails.stream().map(e -> new EmailAddress().setValue(e)).toList();
                person.setEmailAddresses(emailList);
            }

            if (phones != null && !phones.isEmpty()) {
                List<PhoneNumber> phoneList = phones.stream().map(p -> new PhoneNumber().setValue(p)).toList();
                person.setPhoneNumbers(phoneList);
            }

            peopleService.people().updateContact(resourceName, person)
                    .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to update contact", e);
        }
    }

    public void deleteContact(OAuth2AuthenticationToken token, String resourceName) {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("google", token.getName());

            if (client == null) {
                throw new RuntimeException("OAuth2 client not found");
            }

            OAuth2AccessToken accessToken = client.getAccessToken();
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());
            peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).build();

            peopleService.people().deleteContact(resourceName).execute();

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to delete contact", e);
        }
    }

}

