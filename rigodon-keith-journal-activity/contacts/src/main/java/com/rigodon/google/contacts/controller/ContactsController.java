package com.rigodon.google.contacts.controller;

import com.rigodon.google.contacts.model.Contact;
import com.rigodon.google.contacts.service.GoogleContactsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contacts")
public class ContactsController {

    private final GoogleContactsService contactsService;

    public ContactsController(GoogleContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @GetMapping
    public String getContacts(OAuth2AuthenticationToken token, Model model) {
        model.addAttribute("contacts", contactsService.getContacts(token));
        return "contacts"; // renders contacts.html
    }

    @GetMapping("/add")
    public String showAddContactForm() {
        return "addContacts"; // make sure the file is named addContacts.html
    }

    @PostMapping("/add")
    public String addContact(OAuth2AuthenticationToken token,
                             @RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String phone) {
        contactsService.addContact(token, name, email, phone);
        return "redirect:/contacts";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam String resourceName, Model model) {
        model.addAttribute("resourceName", resourceName);
        return "editContact"; // assumes editContact.html exists
    }

    @PostMapping("/edit")
    public String updateContact(OAuth2AuthenticationToken token,
                                @RequestParam String resourceName,
                                @RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String phone) {
        contactsService.updateContact(token, resourceName, name, email, phone);
        return "redirect:/contacts";
    }

    @GetMapping("/delete")
    public String deleteContact(@RequestParam String resourceName, OAuth2AuthenticationToken token) {
        contactsService.deleteContact(token, resourceName);
        return "redirect:/contacts";
    }
}
