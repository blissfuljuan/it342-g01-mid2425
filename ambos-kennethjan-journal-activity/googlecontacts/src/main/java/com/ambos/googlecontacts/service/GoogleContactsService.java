package com.ambos.googlecontacts.service;

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
import java.util.stream.Collectors;

@Service
public class GoogleContactsService {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private PeopleService peopleService;

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
                return client.getAccessToken().getTokenValue();
            }
        }
        throw new RuntimeException("OAuth2 authentication failed!");
    }

    private PeopleService getPeopleService() {
        if (peopleService == null) {
            peopleService = new PeopleService.Builder(
                    new com.google.api.client.http.javanet.NetHttpTransport(),
                    new com.google.api.client.json.gson.GsonFactory(),
                    request -> request.getHeaders().setAuthorization("Bearer " + getAccessToken())
            ).setApplicationName("Google Contacts App").build();
        }
        return peopleService;
    }

    public List<Person> getContacts() throws IOException {
        try {
            ListConnectionsResponse response = getPeopleService().people().connections()
                    .list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return response.getConnections() != null ? response.getConnections() : new ArrayList<>();
        } catch (IOException e) {
            throw new IOException("Failed to retrieve contacts", e);
        }
    }

    public Person createContactWithEmailsAndPhones(
            String givenName,
            String familyName,
            List<String> emails,
            List<String> phones) throws IOException {
        try {
            Person newPerson = new Person();

            Name name = new Name();
            name.setGivenName(givenName);
            name.setFamilyName(familyName);
            newPerson.setNames(List.of(name));

            if (emails != null && !emails.isEmpty()) {
                List<EmailAddress> emailAddresses = emails.stream()
                        .filter(email -> email != null && !email.trim().isEmpty())
                        .map(email -> {
                            EmailAddress emailAddress = new EmailAddress();
                            emailAddress.setValue(email);
                            return emailAddress;
                        })
                        .collect(Collectors.toList());

                if (!emailAddresses.isEmpty()) {
                    newPerson.setEmailAddresses(emailAddresses);
                }
            }

            if (phones != null && !phones.isEmpty()) {
                List<PhoneNumber> phoneNumbers = phones.stream()
                        .filter(phone -> phone != null && !phone.trim().isEmpty())
                        .map(phone -> {
                            PhoneNumber phoneNumber = new PhoneNumber();
                            phoneNumber.setValue(phone);
                            return phoneNumber;
                        })
                        .collect(Collectors.toList());

                if (!phoneNumbers.isEmpty()) {
                    newPerson.setPhoneNumbers(phoneNumbers);
                }
            }

            return getPeopleService().people().createContact(newPerson).execute();
        } catch (IOException e) {
            throw new IOException("Failed to create contact", e);
        }
    }

    public void updateContactWithEmailsAndPhones(
            String resourceName,
            String givenName,
            String familyName,
            List<String> emails,
            List<String> phones) throws IOException {
        try {
            Person existingContact = getPeopleService().people().get(resourceName)
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            Person updatedContact = new Person();
            updatedContact.setEtag(existingContact.getEtag());

            updatedContact.setNames(List.of(new Name()
                    .setGivenName(givenName)
                    .setFamilyName(familyName)));

            if (emails != null) {
                List<EmailAddress> emailAddresses = emails.stream()
                        .filter(email -> email != null && !email.trim().isEmpty())
                        .map(email -> new EmailAddress().setValue(email))
                        .collect(Collectors.toList());
                updatedContact.setEmailAddresses(emailAddresses);
            }

            if (phones != null) {
                List<PhoneNumber> phoneNumbers = phones.stream()
                        .filter(phone -> phone != null && !phone.trim().isEmpty())
                        .map(phone -> new PhoneNumber().setValue(phone))
                        .collect(Collectors.toList());
                updatedContact.setPhoneNumbers(phoneNumbers);
            }

            getPeopleService().people().updateContact(resourceName, updatedContact)
                    .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                    .execute();
        } catch (IOException e) {
            throw new IOException("Failed to update contact", e);
        }
    }

    public void deleteContact(String resourceName) throws IOException {
        try {
            getPeopleService().people().deleteContact(resourceName).execute();
        } catch (IOException e) {
            throw new IOException("Failed to delete contact", e);
        }
    }
}