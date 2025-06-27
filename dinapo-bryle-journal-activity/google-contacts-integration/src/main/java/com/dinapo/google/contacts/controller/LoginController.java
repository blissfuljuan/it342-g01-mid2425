package com.dinapo.google.contacts.controller;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private static final String AUTHORIZATION_BASE_URI = "oauth2/authorization";
    private final ClientRegistrationRepository clientRegistrationRepository;

    public LoginController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        System.out.println("LoginController: preparing dynamic OAuth2 links");

        Map<String, String> oauth2Links = new HashMap<>();

        Iterable<ClientRegistration> registrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE
                && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            registrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        if (registrations != null) {
            registrations.forEach(reg ->
                    oauth2Links.put(reg.getClientName(),
                            AUTHORIZATION_BASE_URI + "/" + reg.getRegistrationId()));
        }

        model.addAttribute("urls", oauth2Links);
        return "components/login";   // templates/components/login.html
    }
}
