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

    @GetMapping
    public String getContacts(OAuth2AuthenticationToken token, Model model) {
        List<Contact> contacts = contactsService.getContacts(token);
        model.addAttribute("contacts", contacts);
        model.addAttribute("newContact", new Contact()); // for form binding
        return "contacts";
    }

    @PostMapping
    public String addContact(@ModelAttribute Contact contact,
                             OAuth2AuthenticationToken token,
                             Model model) {
        System.out.println("Received new contact: " + contact.getName());
        // No saving yet - just reloading for now
        List<Contact> contacts = contactsService.getContacts(token);
        model.addAttribute("contacts", contacts);
        model.addAttribute("newContact", new Contact());
        return "contacts";
    }
}
