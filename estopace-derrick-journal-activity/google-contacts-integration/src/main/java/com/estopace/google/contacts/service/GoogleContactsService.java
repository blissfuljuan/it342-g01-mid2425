package com.estopace.google.contacts.service;

import com.estopace.google.contacts.model.Contact;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GoogleContactsService {
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final OAuth2AuthorizedClientService authorizedClientService;

    public GoogleContactsService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    private PeopleService getPeopleService(OAuth2AuthenticationToken token) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("google", token.getName());

        if (client == null) {
            throw new RuntimeException("OAuth2 client not found");
        }

        OAuth2AccessToken accessToken = client.getAccessToken();
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken.getTokenValue());

        return new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("Google Contacts Integration")
                .build();
    }

    // READ - Get all contacts
    public List<Contact> getContacts(OAuth2AuthenticationToken token) {
        try {
            PeopleService peopleService = getPeopleService(token);

            ListConnectionsResponse response = peopleService.people().connections()
                    .list("people/me")
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .setPageSize(500)
                    .execute();

            return parseContacts(response.getConnections());
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error fetching contacts: " + e.getMessage(), e);
        }
    }

    // READ - Get single contact by resource name
    public Contact getContact(OAuth2AuthenticationToken token, String resourceName) {
        try {
            PeopleService peopleService = getPeopleService(token);

            Person person = peopleService.people()
                    .get(resourceName)
                    .setPersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return parseContact(person);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error fetching contact: " + e.getMessage(), e);
        }
    }

    // CREATE - Add new contact
    public Contact createContact(OAuth2AuthenticationToken token, Contact contact) {
        try {
            PeopleService peopleService = getPeopleService(token);

            Person person = buildPersonFromContact(contact);

            Person createdPerson = peopleService.people()
                    .createContact(person)
                    .execute();

            return parseContact(createdPerson);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error creating contact: " + e.getMessage(), e);
        }
    }

    // UPDATE - Update existing contact
    public Contact updateContact(OAuth2AuthenticationToken token, Contact contact) {
        try {
            PeopleService peopleService = getPeopleService(token);

            // Get the current contact with all its data and metadata
            Person currentPerson = peopleService.people()
                    .get(contact.getResourceName())
                    .setPersonFields("names,emailAddresses,phoneNumbers,metadata")
                    .execute();

            // Start with the current person to preserve all existing data and etag
            Person updatedPerson = currentPerson.clone();

            // Update names - handle both given name and family name
            if ((contact.getFirstName() != null && !contact.getFirstName().trim().isEmpty()) ||
                    (contact.getLastName() != null && !contact.getLastName().trim().isEmpty())) {

                Name name = new Name();

                // Set given name (first name)
                if (contact.getFirstName() != null && !contact.getFirstName().trim().isEmpty()) {
                    name.setGivenName(contact.getFirstName().trim());
                }

                // Set family name (last name)
                if (contact.getLastName() != null && !contact.getLastName().trim().isEmpty()) {
                    name.setFamilyName(contact.getLastName().trim());
                }

                // Construct display name from first and last name
                StringBuilder displayNameBuilder = new StringBuilder();
                if (contact.getFirstName() != null && !contact.getFirstName().trim().isEmpty()) {
                    displayNameBuilder.append(contact.getFirstName().trim());
                }
                if (contact.getLastName() != null && !contact.getLastName().trim().isEmpty()) {
                    if (displayNameBuilder.length() > 0) {
                        displayNameBuilder.append(" ");
                    }
                    displayNameBuilder.append(contact.getLastName().trim());
                }

                if (displayNameBuilder.length() > 0) {
                    name.setDisplayName(displayNameBuilder.toString());
                }

                updatedPerson.setNames(Arrays.asList(name));
            }

            // Update email addresses
            if (contact.getEmails() != null) {
                List<EmailAddress> emailAddresses = new ArrayList<>();
                for (String email : contact.getEmails()) {
                    if (email != null && !email.trim().isEmpty()) {
                        EmailAddress emailAddress = new EmailAddress();
                        emailAddress.setValue(email.trim());
                        emailAddress.setType("home"); // or "work", "other"
                        emailAddresses.add(emailAddress);
                    }
                }
                updatedPerson.setEmailAddresses(emailAddresses);
            }

            // Update phone numbers
            if (contact.getPhones() != null) {
                List<PhoneNumber> phoneNumbers = new ArrayList<>();
                for (String phone : contact.getPhones()) {
                    if (phone != null && !phone.trim().isEmpty()) {
                        PhoneNumber phoneNumber = new PhoneNumber();
                        phoneNumber.setValue(phone.trim());
                        phoneNumber.setType("home"); // or "work", "mobile", "other"
                        phoneNumbers.add(phoneNumber);
                    }
                }
                updatedPerson.setPhoneNumbers(phoneNumbers);
            }

            // The etag should already be set from the cloned currentPerson
            System.out.println("Using etag: " + updatedPerson.getEtag());

            Person result = peopleService.people()
                    .updateContact(contact.getResourceName(), updatedPerson)
                    .setUpdatePersonFields("names,emailAddresses,phoneNumbers")
                    .execute();

            return parseContact(result);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error updating contact: " + e.getMessage(), e);
        }
    }

    // DELETE - Delete contact
    public void deleteContact(OAuth2AuthenticationToken token, String resourceName) {
        try {
            PeopleService peopleService = getPeopleService(token);

            peopleService.people()
                    .deleteContact(resourceName)
                    .execute();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Error deleting contact: " + e.getMessage(), e);
        }
    }

    private Person buildPersonFromContact(Contact contact) {
        Person person = new Person();

        // Set names
        if (contact.getFirstName() != null || contact.getLastName() != null) {
            Name name = new Name();
            name.setGivenName(contact.getFirstName());
            name.setFamilyName(contact.getLastName());
            person.setNames(Arrays.asList(name));
        }

        // Set email addresses
        if (contact.getEmails() != null && !contact.getEmails().isEmpty()) {
            List<EmailAddress> emailAddresses = new ArrayList<>();
            for (String email : contact.getEmails()) {
                if (email != null && !email.trim().isEmpty()) {
                    EmailAddress emailAddress = new EmailAddress();
                    emailAddress.setValue(email.trim());
                    emailAddress.setType("work");
                    emailAddresses.add(emailAddress);
                }
            }
            if (!emailAddresses.isEmpty()) {
                person.setEmailAddresses(emailAddresses);
            }
        }

        // Set phone numbers
        if (contact.getPhones() != null && !contact.getPhones().isEmpty()) {
            List<PhoneNumber> phoneNumbers = new ArrayList<>();
            for (String phone : contact.getPhones()) {
                if (phone != null && !phone.trim().isEmpty()) {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setValue(phone.trim());
                    phoneNumber.setType("work");
                    phoneNumbers.add(phoneNumber);
                }
            }
            if (!phoneNumbers.isEmpty()) {
                person.setPhoneNumbers(phoneNumbers);
            }
        }

        return person;
    }

    private List<Contact> parseContacts(List<Person> persons) {
        List<Contact> contactsList = new ArrayList<>();

        if (persons != null) {
            for (Person person : persons) {
                contactsList.add(parseContact(person));
            }
        }

        return contactsList;
    }

    private Contact parseContact(Person person) {
        Contact contact = new Contact();
        contact.setResourceName(person.getResourceName());

        // Parse names
        if (person.getNames() != null && !person.getNames().isEmpty()) {
            Name name = person.getNames().get(0);
            contact.setFirstName(name.getGivenName());
            contact.setLastName(name.getFamilyName());
            contact.setName(name.getDisplayName());
        }

        // Parse emails
        List<String> emails = new ArrayList<>();
        if (person.getEmailAddresses() != null) {
            for (EmailAddress email : person.getEmailAddresses()) {
                emails.add(email.getValue());
            }
        }
        contact.setEmails(emails);

        // Parse phones
        List<String> phones = new ArrayList<>();
        if (person.getPhoneNumbers() != null) {
            for (PhoneNumber phone : person.getPhoneNumbers()) {
                phones.add(phone.getValue());
            }
        }
        contact.setPhones(phones);

        return contact;
    }
}