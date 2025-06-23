package com.rivera.OAuth2Login.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleContactsService {

    // 🎴 Secret scroll that holds all the OAuth2 magic powers
    private final OAuth2AuthorizedClientService authorizedClientService;

    // 🌟 Constructor injection – passing chakra to the service
    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    // 🔐 Unlocks the forbidden library (Google People API) using the user's token
    private PeopleService getPeopleService(OAuth2AuthenticationToken authentication)
            throws IOException, GeneralSecurityException {

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        @SuppressWarnings("deprecation")
        GoogleCredential credential = new GoogleCredential().setAccessToken(client.getAccessToken().getTokenValue());

        return new PeopleService.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("ContactManager") // 🏯 Your ninja village name
                .build();
    }

    // 🧾 Summon all the user's nakamas (contacts)
    public List<Person> getContacts(OAuth2AuthenticationToken authentication)
            throws IOException, GeneralSecurityException {
        PeopleService peopleService = getPeopleService(authentication);

        ListConnectionsResponse response = peopleService.people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses,phoneNumbers")
                .execute();

        return response.getConnections() != null ? response.getConnections() : Collections.emptyList();
    }

    // 🧙 Create a brand new nakama to add to the team
    public Person createContact(OAuth2AuthenticationToken auth, String givenName, String familyName,
                                List<String> emailAddresses, List<String> phoneNumbers)
            throws IOException, GeneralSecurityException {

        PeopleService peopleService = getPeopleService(auth);
        Person contactToCreate = new Person();

        contactToCreate.setNames(Collections.singletonList(
                new Name().setGivenName(givenName).setFamilyName(familyName)
        ));

        if (emailAddresses != null && !emailAddresses.isEmpty()) {
            List<EmailAddress> emails = new ArrayList<>();
            for (String email : emailAddresses) {
                if (email != null && !email.trim().isEmpty()) {
                    emails.add(new EmailAddress().setValue(email.trim()));
                }
            }
            contactToCreate.setEmailAddresses(emails);
        }

        if (phoneNumbers != null && !phoneNumbers.isEmpty()) {
            List<PhoneNumber> phones = new ArrayList<>();
            for (String phone : phoneNumbers) {
                if (phone != null && !phone.trim().isEmpty()) {
                    phones.add(new PhoneNumber().setValue(phone.trim()));
                }
            }
            contactToCreate.setPhoneNumbers(phones);
        }

        return peopleService.people().createContact(contactToCreate).execute();
    }


    // 🔁 Revise an existing nakama's attributes
    public Person updateContact(OAuth2AuthenticationToken authentication, String resourceName,
                                String givenName, String familyName, String emailAddress, String phoneNumber)
            throws IOException, GeneralSecurityException {

        PeopleService peopleService = getPeopleService(authentication);

        // 🔍 Find the original contact using chakra sensing
        Person existingContact = peopleService.people().get(resourceName)
                .setPersonFields("names,emailAddresses,phoneNumbers,metadata")
                .execute();

        if (existingContact == null) {
            throw new IOException("Contact not found: " + resourceName);
        }

        // 🛠️ Prepare new form to overwrite existing ninja data
        Person contactToUpdate = new Person();
        contactToUpdate.setResourceName(resourceName);

        // 🧪 Apply the sacred ETag seal for safe updating
        if (existingContact.getEtag() != null) {
            contactToUpdate.setEtag(existingContact.getEtag());
        }

        // 🧠 Rename the shinobi (if needed)
        if (givenName != null || familyName != null) {
            List<Name> names = new ArrayList<>();
            Name name = new Name().setGivenName(givenName).setFamilyName(familyName);

            if (existingContact.getNames() != null && !existingContact.getNames().isEmpty()) {
                name.setMetadata(existingContact.getNames().get(0).getMetadata());
            }
            names.add(name);
            contactToUpdate.setNames(names);
        }

        // 📱 Update their communication crystal
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            PhoneNumber number = new PhoneNumber().setValue(phoneNumber);

            if (existingContact.getPhoneNumbers() != null && !existingContact.getPhoneNumbers().isEmpty()) {
                number.setMetadata(existingContact.getPhoneNumbers().get(0).getMetadata());
            }
            phoneNumbers.add(number);
            contactToUpdate.setPhoneNumbers(phoneNumbers);
        }

        // 📧 Adjust their magic mail scroll
        if (emailAddress != null && !emailAddress.isEmpty()) {
            List<EmailAddress> emailAddresses = new ArrayList<>();
            EmailAddress email = new EmailAddress().setValue(emailAddress);

            if (existingContact.getEmailAddresses() != null && !existingContact.getEmailAddresses().isEmpty()) {
                email.setMetadata(existingContact.getEmailAddresses().get(0).getMetadata());
            }
            emailAddresses.add(email);
            contactToUpdate.setEmailAddresses(emailAddresses);
        }

        // 🧙‍♂️ Update the contact in the Great Google Scroll
        return peopleService.people().updateContact(resourceName, contactToUpdate)
                .setUpdatePersonFields("names,phoneNumbers,emailAddresses")
                .execute();
    }

    // 💀 Delete a fallen warrior from the scroll
    public void deleteContact(OAuth2AuthenticationToken authentication, String resourceName)
            throws IOException, GeneralSecurityException {
        PeopleService peopleService = getPeopleService(authentication);
        peopleService.people().deleteContact(resourceName).execute();
    }

    // 🔍 Spyglass jutsu – read a single contact's soul
    public Person getContact(OAuth2AuthenticationToken authentication, String resourceName)
            throws IOException, GeneralSecurityException {
        PeopleService peopleService = getPeopleService(authentication);

        return peopleService.people().get(resourceName)
                .setPersonFields("names,emailAddresses,phoneNumbers")
                .execute();
    }
}
