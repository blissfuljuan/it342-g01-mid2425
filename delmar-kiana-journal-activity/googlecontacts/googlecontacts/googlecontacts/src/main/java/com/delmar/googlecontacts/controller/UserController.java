package com.delmar.googlecontacts.controller;

import com.delmar.googlecontacts.Service.GoogleContactsService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private GoogleContactsService googleContactsService;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("")
    public String index() {
        return "home";  // Return home view
    }

    @GetMapping("/user-info")
    public String getUserInfo(@AuthenticationPrincipal Object principal, Model model) {
        if (principal instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) principal;
            // Extract OIDC user info
            String name = oidcUser.getFullName();
            String email = oidcUser.getEmail();
            String pictureUrl = oidcUser.getPicture();

            // Add attributes to model
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("pictureUrl", pictureUrl);
        } else if (principal instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) principal;
            // Extract OAuth2 user info
            String name = oauth2User.getAttribute("name");
            String email = oauth2User.getAttribute("email");
            String pictureUrl = oauth2User.getAttribute("picture");

            // Add attributes to model
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("pictureUrl", pictureUrl);
        } else {
            return "redirect:/"; // Redirect if not authenticated
        }

        return "user-info"; // Return the view name
    }

    @GetMapping("/contacts")
    public String fetchContactsFromGoogle(Model model, @AuthenticationPrincipal OAuth2User principal) throws IOException {
        if (principal == null) {
            return "redirect:/";
        }

        List<Person> connections = googleContactsService.getConnectionsAsPeople(principal);
        model.addAttribute("contacts", connections);
        return "contact";
    }

    @GetMapping("/contact/add-form")
    public String showAddContactForm(Model model) {
        return "add-contact";
    }

    @PostMapping("/contact/add")
    public String addContact(
            @RequestParam("displayName") String name,
            @RequestParam("emailList") List<String> emailList,
            @RequestParam("phoneList") List<String> phoneList,
            @AuthenticationPrincipal OAuth2User principal,
            Model model) {

        try {
            googleContactsService.addContact(principal, name, emailList, phoneList);
            return "redirect:/contacts";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add contact: " + e.getMessage());
            return "add-contact";
        }
    }


    @GetMapping("/contacts/edit/people/{contactId}")
    public String editContactForm(
            @PathVariable String contactId,
            @AuthenticationPrincipal OAuth2User principal,
            Model model) {

        try {
            // Fetch the contact by ID
            Person contact = googleContactsService.getPersonById(principal, "people/" + contactId);

            if (contact == null) {
                throw new RuntimeException("Contact not found.");
            }

            // Add the contact to the model
            model.addAttribute("contact", contact);

            // Return the edit form template
            return "edit-contact";

        } catch (RuntimeException e) {
            // Log the error and add an error message to the model
            model.addAttribute("error", "Failed to load contact: " + e.getMessage());
            return "redirect:/contacts"; // Redirect to the contacts list with an error message
        }
    }

    // POST: Handle the form submission
    @PostMapping("/contacts/edit/people/{contactId}")
    public String updateContact(
            @PathVariable String contactId,
            @RequestParam String displayName,
            @RequestParam(name = "emailList", required = false) List<String> emailList,
            @RequestParam(name = "phoneList", required = false) List<String> phoneList,
            @AuthenticationPrincipal OAuth2User principal,
            Model model) {

        try {
            Person existingContact = googleContactsService.getPersonById(principal, "people/" + contactId);
            if (existingContact == null) throw new RuntimeException("Contact not found.");

            Person updatePerson = new Person();
            updatePerson.setEtag(existingContact.getEtag());

            List<String> updateFields = new ArrayList<>();

            if (displayName != null && !displayName.trim().isEmpty()) {
                Name personName = new Name();
                personName.setDisplayName(displayName.trim());
                personName.setGivenName(displayName.trim());
                updatePerson.setNames(List.of(personName));
                updateFields.add("names");
            }

            if (emailList != null && !emailList.isEmpty()) {
                List<EmailAddress> emails = emailList.stream()
                        .filter(e -> e != null && !e.trim().isEmpty())
                        .map(e -> new EmailAddress().setValue(e.trim()).setType("home"))
                        .toList();
                if (!emails.isEmpty()) {
                    updatePerson.setEmailAddresses(emails);
                    updateFields.add("emailAddresses");
                }
            }

            if (phoneList != null && !phoneList.isEmpty()) {
                List<PhoneNumber> phones = phoneList.stream()
                        .filter(p -> p != null && !p.trim().isEmpty())
                        .map(p -> new PhoneNumber().setValue(p.trim()).setType("mobile"))
                        .toList();
                if (!phones.isEmpty()) {
                    updatePerson.setPhoneNumbers(phones);
                    updateFields.add("phoneNumbers");
                }
            }

            if (updateFields.isEmpty()) throw new RuntimeException("No fields provided for update.");

            googleContactsService.updateContact(principal, "people/" + contactId, updatePerson, updateFields);
            return "redirect:/contacts";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Failed to update contact: " + e.getMessage());
            return "edit-contact";
        }
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

    @GetMapping("/contacts/delete/people/{contactId}")
    public String deleteContact(
            @PathVariable String contactId,
            @AuthenticationPrincipal OAuth2User principal) {
        try {
            googleContactsService.deleteContact(principal, "people/" + contactId);
        } catch (Exception e) {
            System.err.println("Error deleting contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }
}