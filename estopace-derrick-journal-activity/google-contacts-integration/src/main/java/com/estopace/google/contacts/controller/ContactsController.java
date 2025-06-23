package com.estopace.google.contacts.controller;

import com.estopace.google.contacts.model.Contact;
import com.estopace.google.contacts.service.GoogleContactsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactsController {
    private final GoogleContactsService contactsService;

    public ContactsController(GoogleContactsService contactsService) {
        this.contactsService = contactsService;
    }

    // READ - Display all contacts
    @GetMapping
    public String getContacts(OAuth2AuthenticationToken token, Model model) {
        try {
            List<Contact> contacts = contactsService.getContacts(token);
            model.addAttribute("contacts", contacts);
            model.addAttribute("success", model.asMap().get("success"));
            model.addAttribute("error", model.asMap().get("error"));
            return "contacts";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading contacts: " + e.getMessage());
            model.addAttribute("contacts", new ArrayList<>());
            return "contacts";
        }
    }

    // CREATE - Show add contact form
    @GetMapping("/add")
    public String showAddContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "addContact";
    }

    // CREATE - Process add contact form
    @PostMapping("/add")
    public String addContact(@ModelAttribute Contact contact,
                             OAuth2AuthenticationToken token,
                             RedirectAttributes redirectAttributes) {
        try {
            // Convert single email/phone strings to lists if needed
            if (contact.getEmails() == null) {
                contact.setEmails(new ArrayList<>());
            }
            if (contact.getPhones() == null) {
                contact.setPhones(new ArrayList<>());
            }

            contactsService.createContact(token, contact);
            redirectAttributes.addFlashAttribute("success", "Contact added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }

    // UPDATE - Show edit contact form
    @GetMapping("/edit/**")
    public String showEditContactForm(HttpServletRequest request,
                                      OAuth2AuthenticationToken token,
                                      Model model) {
        try {
            // Extract the resource name from the URL path
            String resourceName = request.getRequestURI().substring("/contacts/edit/".length());
            // Decode the resource name (it may be URL encoded)
            String decodedResourceName = java.net.URLDecoder.decode(resourceName, "UTF-8");
            Contact contact = contactsService.getContact(token, decodedResourceName);
            model.addAttribute("contact", contact);
            return "editContact";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading contact for edit: " + e.getMessage());
            return "redirect:/contacts";
        }
    }

    // UPDATE - Process edit contact form
    @PostMapping("/edit")
    public String updateContact(@ModelAttribute Contact contact,
                                OAuth2AuthenticationToken token,
                                RedirectAttributes redirectAttributes) {
        try {
            contactsService.updateContact(token, contact);
            redirectAttributes.addFlashAttribute("success", "Contact updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }

    // DELETE - Delete contact
    @PostMapping("/delete/")
    public String deleteContact(String resourceName,
                                OAuth2AuthenticationToken token,
                                RedirectAttributes redirectAttributes) {
        try {
            // Decode the resource name (it may be URL encoded)
            String decodedResourceName = java.net.URLDecoder.decode(resourceName, "UTF-8");
            contactsService.deleteContact(token, decodedResourceName);
            redirectAttributes.addFlashAttribute("success", "Contact deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting contact: " + e.getMessage());
        }
        return "redirect:/contacts";
    }

    // Utility endpoint for adding email/phone fields dynamically (for AJAX)
    @PostMapping("/add-email")
    @ResponseBody
    public String addEmailField() {
        return "<input type=\"email\" name=\"emails\" class=\"form-control mb-2\" placeholder=\"Email address\">";
    }

    @PostMapping("/add-phone")
    @ResponseBody
    public String addPhoneField() {
        return "<input type=\"tel\" name=\"phones\" class=\"form-control mb-2\" placeholder=\"Phone number\">";
    }
}