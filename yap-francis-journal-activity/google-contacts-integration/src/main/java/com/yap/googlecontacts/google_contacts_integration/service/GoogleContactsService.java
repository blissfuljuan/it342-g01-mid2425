package com.yap.googlecontacts.google_contacts_integration.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    private String getAccessToken(OAuth2User principal) {
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
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

    private PeopleService buildPeopleService(String accessToken) {
        return new PeopleService.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                request -> request.getHeaders().setAuthorization("Bearer " + accessToken)
        ).setApplicationName("Google Contacts Integration").build();
    }

    public List<Person> getConnectionsAsPeople(OAuth2User principal) throws IOException {
        String accessToken = getAccessToken(principal);
        PeopleService peopleService = buildPeopleService(accessToken);

        ListConnectionsResponse response = peopleService.people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses,phoneNumbers")
                .execute();

        return response.getConnections() != null ? response.getConnections() : new ArrayList<>();
    }

    public void addContact(OAuth2User principal, String name, String email, String phoneNumber) throws IOException {
        String accessToken = getAccessToken(principal);
        PeopleService peopleService = buildPeopleService(accessToken);

        Person contactToCreate = new Person();

        Name personName = new Name();
        personName.setDisplayName(name);
        personName.setGivenName(name);
        contactToCreate.setNames(List.of(personName));

        if (email != null && !email.isEmpty()) {
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.setValue(email);
            contactToCreate.setEmailAddresses(List.of(emailAddress));
        }

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            PhoneNumber phone = new PhoneNumber();
            phone.setValue(phoneNumber);
            contactToCreate.setPhoneNumbers(List.of(phone));
        }

        peopleService.people().createContact(contactToCreate).execute();
    }

    public Person getPersonById(OAuth2User principal, String resourceName) throws IOException {
        String accessToken = getAccessToken(principal);
        PeopleService peopleService = buildPeopleService(accessToken);

        return peopleService.people().get(resourceName)
                .setPersonFields("names,emailAddresses,phoneNumbers")
                .execute();
    }

    public void updateContact(OAuth2User principal, String resourceName, Person updatedPerson, List<String> updateFields) throws IOException {
        String accessToken = getAccessToken(principal);
        PeopleService peopleService = buildPeopleService(accessToken);

        peopleService.people().updateContact(resourceName, updatedPerson)
                .setUpdatePersonFields(String.join(",", updateFields))
                .execute();
    }

    public void deleteContact(OAuth2User principal, String resourceName) throws IOException {
        String accessToken = getAccessToken(principal);
        PeopleService peopleService = buildPeopleService(accessToken);

        peopleService.people().deleteContact(resourceName).execute();
    }
}
