package com.example.demo.controller;

import com.example.demo.service.GoogleContactsService;
import com.google.api.services.people.v1.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactsController {
    @Autowired
    private GoogleContactsService googleContactsService;

    @GetMapping
public String GetContacts(OAuth2AuthenticationToken token, Model model) throws GeneralSecurityException, IOException {
        List<Person> contacts = googleContactsService.gitcontacts(token);
        model.addAttribute("persons", contacts);
        return "usercontacts";
    }

}
