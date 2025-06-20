package com.yap.googlecontacts.google_contacts_integration.controller;

import com.yap.googlecontacts.google_contacts_integration.service.GoogleContactsService;

import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Controller
public class UserController {

    @Autowired
    private GoogleContactsService googleContactsService;

    @GetMapping("")
    public String index() {
        return "home";
    }

    @GetMapping("/user-info")
    public String getUserInfo(@AuthenticationPrincipal Object principal, Model model) {
        if (principal instanceof OidcUser oidcUser) {
            model.addAttribute("name", oidcUser.getFullName());
            model.addAttribute("email", oidcUser.getEmail());
            model.addAttribute("pictureUrl", oidcUser.getPicture());
        } else if (principal instanceof OAuth2User oauth2User) {
            model.addAttribute("name", oauth2User.getAttribute("name"));
            model.addAttribute("email", oauth2User.getAttribute("email"));
            model.addAttribute("pictureUrl", oauth2User.getAttribute("picture"));
        } else {
            return "redirect:/";
        }
        return "user-info";
    }

    @GetMapping("/contacts")
    public String fetchContacts(Model model, @AuthenticationPrincipal OAuth2User principal) throws IOException {
        List<Person> connections = googleContactsService.getConnectionsAsPeople(principal);
        model.addAttribute("contacts", connections);
        return "contact";
    }

    @GetMapping("/contact/add-form")
    public String showAddForm() {
        return "addContact";
    }

    @PostMapping("/contact/add")
    public String addContact(@RequestParam String displayName,
                             @RequestParam String email,
                             @RequestParam(required = false) String phoneNumber,
                             @AuthenticationPrincipal OAuth2User principal,
                             Model model) {
        try {
            googleContactsService.addContact(principal, displayName, email, phoneNumber);
            return "redirect:/contacts";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add contact: " + e.getMessage());
            return "addContact";
        }
    }

    @GetMapping("/contacts/edit/people/{contactId}")
    public String editForm(@PathVariable String contactId,
                           @AuthenticationPrincipal OAuth2User principal,
                           Model model) {
        try {
            Person contact = googleContactsService.getPersonById(principal, "people/" + contactId);
            model.addAttribute("contact", contact);
            return "editContact";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load contact: " + e.getMessage());
            return "redirect:/contacts";
        }
    }

    @PostMapping("/contacts/edit/people/{contactId}")
    public String updateContact(@PathVariable String contactId,
                                @RequestParam String displayName,
                                @RequestParam String email,
                                @RequestParam String phoneNumber,
                                @AuthenticationPrincipal OAuth2User principal,
                                Model model) {
        try {
            Person updatePerson = new Person();
            Person original = googleContactsService.getPersonById(principal, "people/" + contactId);
            updatePerson.setEtag(original.getEtag());

            updatePerson.setNames(List.of(new Name().setDisplayName(displayName).setGivenName(displayName)));
            updatePerson.setEmailAddresses(List.of(new EmailAddress().setValue(email).setType("home")));
            updatePerson.setPhoneNumbers(List.of(new PhoneNumber().setValue(phoneNumber).setType("mobile")));

            googleContactsService.updateContact(
                    principal,
                    "people/" + contactId,
                    updatePerson,
                    List.of("names", "emailAddresses", "phoneNumbers")
            );

            return "redirect:/contacts";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update contact: " + e.getMessage());
            return "editContact";
        }
    }

    @GetMapping("/contacts/delete/people/{contactId}")
    public String deleteContact(@PathVariable String contactId, @AuthenticationPrincipal OAuth2User principal) {
        try {
            googleContactsService.deleteContact(principal, "people/" + contactId);
        } catch (Exception e) {
            System.err.println("Error deleting contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }

    @DeleteMapping("/contacts/delete/{contactId}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteContactAjax(
            @PathVariable String contactId,
            @AuthenticationPrincipal OAuth2User principal) {
        Map<String, String> response = new HashMap<>();
        try {
            googleContactsService.deleteContact(principal, "people/" + contactId);
            response.put("status", "success");
            response.put("message", "Contact deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}