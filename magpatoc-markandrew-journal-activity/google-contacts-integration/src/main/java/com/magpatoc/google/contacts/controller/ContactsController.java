package com.magpatoc.google.contacts.controller;

import com.magpatoc.google.contacts.model.Contact;
import com.magpatoc.google.contacts.service.GoogleContactsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    private final GoogleContactsService contactsService;

    public ContactsController(GoogleContactsService contactsService) {
        this.contactsService = contactsService;
    }

    // Display all contacts and form
    @GetMapping
    public String getContacts(OAuth2AuthenticationToken token, Model model) {
        List<Contact> contacts = contactsService.getContacts(token);
        model.addAttribute("contacts", contacts);
        model.addAttribute("newContact", new Contact());
        return "contacts";
    }

    // Add new contact
    @PostMapping
    public String addContact(@ModelAttribute("newContact") Contact contact,
                             OAuth2AuthenticationToken token) {
        contactsService.addContact(token, contact);
        return "redirect:/contacts";
    }

    // Update contact (contact.id can contain slashes like "people/c123")
    @PostMapping("/update/{id:.+}")
    public String updateContact(@PathVariable String id,
                                @ModelAttribute Contact contact,
                                OAuth2AuthenticationToken token) {
        contact.setId(id);
        contactsService.updateContact(token, contact);
        return "redirect:/contacts";
    }

    // Delete contact (resourceName with slashes supported)
    @GetMapping("/delete/{id:.+}")
    public String deleteContact(@PathVariable String id,
                                OAuth2AuthenticationToken token) {
        contactsService.deleteContact(token, id);
        return "redirect:/contacts";
    }
}
