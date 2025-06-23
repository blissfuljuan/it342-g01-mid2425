package com.rivera.OAuth2Login.Controller;

import com.google.api.services.people.v1.model.Person;
import com.rivera.OAuth2Login.Service.GoogleContactsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
public class ContactViewController {

    private final GoogleContactsService contactsService;

    // 🧙‍♂️ Dependency injection: binding your contact jutsu into this controller class
    public ContactViewController(GoogleContactsService contactsService) {
        this.contactsService = contactsService;
    }

    // 🏠 The home route — where the story begins!
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 📜 Summon the nakama list (get all contacts)
    @GetMapping("/contacts")
    public String contacts(OAuth2AuthenticationToken authentication, Model model) {
        try {
            List<Person> contacts = contactsService.getContacts(authentication);
            model.addAttribute("contacts", contacts);
            return "contacts"; // 🧾 Show the scroll of all allies
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to fetch contacts: " + e.getMessage());
            return "error"; // 😨 Something interrupted the summoning
        }
    }

    // ✨ Prepare the summoning circle (form for new nakama)
    @GetMapping("/contacts/new")
    public String newContactForm() {
        return "new-contact";
    }

    // 🪄 Cast the add-nakama spell
    @PostMapping("/contacts/new")
    public String createContact(
            OAuth2AuthenticationToken authentication,
            @RequestParam String givenName,
            @RequestParam String familyName,
            @RequestParam(required = false) List<String> emailAddresses,
            @RequestParam(required = false) List<String> phoneNumbers) {
        try {
            contactsService.createContact(authentication, givenName, familyName, emailAddresses, phoneNumbers);
            return "redirect:/contacts";
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return "redirect:/error?message=" + e.getMessage();
        }
    }

    // 🛠️ Load the gear: prepare to update an ally’s profile
    @GetMapping("/contacts/edit")
    public String editContactForm(@RequestParam String resourceName, Model model,
                                  OAuth2AuthenticationToken authentication) {
        try {
            Person contact = contactsService.getContact(authentication, resourceName);
            model.addAttribute("contact", contact);
            model.addAttribute("resourceName", resourceName);
            return "edit-contact"; // ⚒️ Let’s power-up this contact
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to fetch contact: " + e.getMessage());
            return "error"; // 🔥 Error arc activated
        }
    }

    // 🧬 Reforging the nakama’s essence
    @PostMapping("/contacts/edit")
    public String updateContact(
            OAuth2AuthenticationToken authentication,
            @RequestParam String resourceName,
            @RequestParam String givenName,
            @RequestParam String familyName,
            @RequestParam(required = false) String emailAddress,
            @RequestParam(required = false) String phoneNumber,
            Model model) {
        try {
            contactsService.updateContact(authentication, resourceName, givenName, familyName, emailAddress, phoneNumber);
            return "redirect:/contacts"; // 🌟 Transformation complete!
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Failed to update contact: " + e.getMessage());
            return "error"; // 🧨 The upgrade failed — fallback to safe zone
        }
    }

    // 💔 Farewell to an old comrade
    @PostMapping("/contacts/delete")
    public String deleteContact(
            OAuth2AuthenticationToken authentication,
            @RequestParam String resourceName) {
        try {
            contactsService.deleteContact(authentication, resourceName);
            return "redirect:/contacts"; // ✨ Their memory will live on... back to the list
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
            return "redirect:/error?message=" + e.getMessage(); // 😔 Couldn’t perform the final release
        }
    }
}
