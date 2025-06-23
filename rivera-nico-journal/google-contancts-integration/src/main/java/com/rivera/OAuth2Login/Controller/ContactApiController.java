package com.rivera.OAuth2Login.Controller;

import com.google.api.services.people.v1.model.Person;
import com.rivera.OAuth2Login.Service.GoogleContactsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contacts")
public class ContactApiController {

    private final GoogleContactsService contactsService;

    public ContactApiController(GoogleContactsService contactsService) {
        this.contactsService = contactsService;
    }

    // 📖 Retrieve all nakama (contacts) for the logged-in senpai
    @GetMapping
    public ResponseEntity<List<Person>> getAllContacts(OAuth2AuthenticationToken authentication) {
        try {
            List<Person> contacts = contactsService.getContacts(authentication);
            return ResponseEntity.ok(contacts);
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("⚠️ Oh no! Couldn't fetch your nakama list. Something went wrong!");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ✨ Add a new contact to the senpai's circle
    @PostMapping
    public ResponseEntity<Person> addContact(
            OAuth2AuthenticationToken authentication,
            @RequestBody Map<String, String> contactDetails) {
        try {
            Person newContact = contactsService.createContact(
                    authentication,
                    contactDetails.get("givenName"),
                    contactDetails.get("familyName"),
                    Collections.singletonList(contactDetails.get("emailAddress")),
                    Collections.singletonList(contactDetails.get("phoneNumber"))
            );
            System.out.println("🌸 New nakama added successfully!");
            return ResponseEntity.ok(newContact);
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("😵‍💫 Contact creation failed! Try using more chakra...");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 🔧 Update your nakama’s details — power up!
    @PutMapping("/{resourceName}")
    public ResponseEntity<Person> updateContact(
            OAuth2AuthenticationToken authentication,
            @PathVariable String resourceName,
            @RequestBody Map<String, String> contactDetails) {
        try {
            Person updatedContact = contactsService.updateContact(
                    authentication,
                    resourceName,
                    contactDetails.get("givenName"),
                    contactDetails.get("familyName"),
                    contactDetails.get("emailAddress"),
                    contactDetails.get("phoneNumber")
            );
            System.out.println("💾 Nakama details updated successfully!");
            return ResponseEntity.ok(updatedContact);
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("⚠️ Failed to level up the contact! Retry, senpai!");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 💀 Remove a contact from your team (farewell, warrior)
    @DeleteMapping("/{resourceName}")
    public ResponseEntity<Void> deleteContact(
            OAuth2AuthenticationToken authentication,
            @PathVariable String resourceName) {
        try {
            contactsService.deleteContact(authentication, resourceName);
            System.out.println("🗑️ Nakama deleted. Until next time...");
            return ResponseEntity.ok().build();
        } catch (IOException | GeneralSecurityException e) {
            System.err.println("❌ Deletion jutsu failed! Something’s blocking it...");
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
