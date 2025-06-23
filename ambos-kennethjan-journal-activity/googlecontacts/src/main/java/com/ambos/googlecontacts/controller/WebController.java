package com.ambos.googlecontacts.controller;

import com.ambos.googlecontacts.service.GoogleContactsService;
import com.google.api.services.people.v1.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    private final GoogleContactsService googleContactsService;

    public WebController(GoogleContactsService googleContactsService) {
        this.googleContactsService = googleContactsService;
    }

    @GetMapping("/contacts")
    public String showContacts(Model model) {
        try {
            List<Person> contacts = googleContactsService.getContacts();
            System.out.println("Fetched contacts: " + contacts.size());
            model.addAttribute("contacts", contacts);
            return "contacts";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to fetch contacts.");
            return "error";
        }
    }

    @PostMapping("/api/contacts/create")
    public String createContact(
            @RequestParam String givenName,
            @RequestParam String familyName,
            @RequestParam(name = "emails", required = false) List<String> emails,
            @RequestParam(name = "phones", required = false) List<String> phones) throws IOException {

        List<String> validEmails = emails != null ?
                emails.stream()
                        .filter(e -> e != null && !e.trim().isEmpty())
                        .collect(Collectors.toList()) :
                Collections.emptyList();

        List<String> validPhones = phones != null ?
                phones.stream()
                        .filter(p -> p != null && !p.trim().isEmpty())
                        .collect(Collectors.toList()) :
                Collections.emptyList();

        googleContactsService.createContactWithEmailsAndPhones(
                givenName,
                familyName,
                validEmails,
                validPhones
        );

        return "redirect:/contacts";
    }

    @PostMapping("/api/contacts/update")
    public String updateContact(
            @RequestParam String resourceName,
            @RequestParam String givenName,
            @RequestParam String familyName,
            @RequestParam(required = false) List<String> emails,
            @RequestParam(required = false) List<String> phones) throws IOException {

        googleContactsService.updateContactWithEmailsAndPhones(
                resourceName,
                givenName,
                familyName,
                emails,
                phones
        );

        return "redirect:/contacts";
    }

    @PostMapping("/api/contacts/delete")
    public String deleteContact(@RequestParam String resourceName) {
        try {
            googleContactsService.deleteContact(resourceName);
            System.out.println("Deleted contact: " + resourceName);
            return "redirect:/contacts";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}