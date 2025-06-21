package com.derecho.oauth2login.service;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleContactsService {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    private String getAccessToken() {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName()
            );
            if (client != null) {
                String token = client.getAccessToken().getTokenValue();
                System.out.println("OAuth2 Access Token: " + token); // DEBUGGING TOKEN
                return token;
            }
        }
        throw new RuntimeException("OAuth2 authentication failed!");
    }

    private PeopleService createPeopleService() {
        return new PeopleService.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                new com.google.api.client.json.gson.GsonFactory(),
                request -> request.getHeaders().setAuthorization("Bearer " + getAccessToken())
        ).setApplicationName("Google Contacts App").build();
    }

    public List<Person> getContacts() throws IOException {
        try {
            PeopleService peopleService = createPeopleService();
            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            List<Person> contacts = response.getConnections() != null ? response.getConnections() : new ArrayList<>();
            System.out.println("Fetched Contacts Count: " + contacts.size()); // DEBUGGING CONTACT COUNT
            return contacts;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error fetching contacts: " + e.getMessage());
            throw new IOException("Failed to retrieve contacts from Google People API", e);
        }
    }

    public Person createContact(String givenName, String familyName, List<String> emails, List<String> phones) throws IOException {
        try {
            PeopleService peopleService = createPeopleService();

            // Create a new Person object
            Person newPerson = new Person();

            // Set the name
            Name name = new Name();
            name.setGivenName(givenName);
            name.setFamilyName(familyName);
            newPerson.setNames(List.of(name));

            // Set the emails
            if (emails != null && !emails.isEmpty()) {
                List<EmailAddress> emailAddresses = emails.stream()
                    .filter(email -> email != null && !email.trim().isEmpty())
                    .map(email -> {
                        EmailAddress emailAddress = new EmailAddress();
                        emailAddress.setValue(email.trim());
                        return emailAddress;
                    })
                    .collect(java.util.stream.Collectors.toList());
                newPerson.setEmailAddresses(emailAddresses);
            }

            // Set the phone numbers
            if (phones != null && !phones.isEmpty()) {
                List<PhoneNumber> phoneNumbers = phones.stream()
                    .filter(phone -> phone != null && !phone.trim().isEmpty())
                    .map(phone -> {
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.setValue(phone.trim());
                        return phoneNumber;
                    })
                    .collect(java.util.stream.Collectors.toList());
                newPerson.setPhoneNumbers(phoneNumbers);
            }

            // Create the contact
            Person createdPerson = peopleService.people().createContact(newPerson).execute();
            System.out.println("Created Contact ID: " + createdPerson.getResourceName());
            return createdPerson;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error creating contact: " + e.getMessage());
            throw new IOException("Failed to create contact using Google People API", e);
        }
    }

    public void updateContact(String resourceName, String givenName, String familyName, List<String> emails, List<String> phones) throws IOException {
        try {
            PeopleService peopleService = createPeopleService();

            // Step 1: Fetch the existing contact to get the etag
            Person existingContact = peopleService.people().get(resourceName)
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            String etag = existingContact.getEtag(); // Extract etag

            // Step 2: Update the contact details
            List<Name> names = new ArrayList<>();
            names.add(new Name().setGivenName(givenName).setFamilyName(familyName));

            // Process emails
            List<EmailAddress> emailAddresses = new ArrayList<>();
            if (emails != null && !emails.isEmpty()) {
                emailAddresses = emails.stream()
                    .filter(email -> email != null && !email.trim().isEmpty())
                    .map(email -> new EmailAddress().setValue(email.trim()))
                    .collect(java.util.stream.Collectors.toList());
            }

            // Process phone numbers
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            if (phones != null && !phones.isEmpty()) {
                phoneNumbers = phones.stream()
                    .filter(phone -> phone != null && !phone.trim().isEmpty())
                    .map(phone -> new PhoneNumber().setValue(phone.trim()))
                    .collect(java.util.stream.Collectors.toList());
            }

            // Step 3: Create a new contact object with the etag
            Person updatedContact = new Person();
            updatedContact.setEtag(etag);
            updatedContact.setNames(names);
            updatedContact.setEmailAddresses(emailAddresses);
            updatedContact.setPhoneNumbers(phoneNumbers);

            // Step 4: Perform the update
            peopleService.people().updateContact(resourceName, updatedContact)
                    .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            System.out.println("Contact updated successfully: " + resourceName);
        } catch (IOException e) {
            System.err.println("Error updating contact: " + e.getMessage());
            throw new IOException("Failed to update contact in Google People API", e);
        }
    }

    public void deleteContact(String resourceName) throws IOException {
        try {
            PeopleService peopleService = createPeopleService();

            // Call the Google People API to delete the contact
            peopleService.people().deleteContact(resourceName).execute();

            System.out.println("Contact deleted successfully: " + resourceName);
        } catch (IOException e) {
            System.err.println("Error deleting contact: " + e.getMessage());
            throw new IOException("Failed to delete contact in Google People API", e);
        }
    }


}