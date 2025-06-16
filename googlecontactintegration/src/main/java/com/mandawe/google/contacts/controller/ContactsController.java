package com.mandawe.google.contacts.controller;

import com.mandawe.google.contacts.model.Contact;
import com.mandawe.google.contacts.service.GoogleContactsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("contacts", contactsService.getContacts(token));
        return "contacts";
    }

    @GetMapping("/add")
    public String showAddContactForm() {
        return "addContact";
    }

    @PostMapping("/add")
    public String addContact(OAuth2AuthenticationToken token,
                           @RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone) {
        contactsService.addContact(token, name, email, phone);
        return "redirect:/contacts";
    }
} 