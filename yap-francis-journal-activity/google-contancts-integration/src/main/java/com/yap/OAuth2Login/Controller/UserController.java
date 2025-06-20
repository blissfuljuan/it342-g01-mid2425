package com.yap.OAuth2Login.Controller;

import com.yap.OAuth2Login.Service.GoogleContactsService;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class UserController {

    @Autowired
    private GoogleContactsService googleContactsService;

    @GetMapping("/")
    public String index() {
        return "redirect:/user-info";
    }

    @GetMapping("/user-info")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null) return "redirect:/";

        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String picture = principal.getAttribute("picture");

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("pictureUrl", picture);

        return "user-info";
    }

    @GetMapping("/contacts")
    public String getContacts(@AuthenticationPrincipal OAuth2User principal, Model model) {
        try {
            List<Person> contacts = googleContactsService.getConnectionsAsPeople(principal);
            model.addAttribute("contacts", contacts);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "contact";
    }
}
