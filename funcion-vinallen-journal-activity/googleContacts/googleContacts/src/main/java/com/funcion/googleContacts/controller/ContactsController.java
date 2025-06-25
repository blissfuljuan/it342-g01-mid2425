package com.funcion.googleContacts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Controller
public class ContactsController {

    @GetMapping("/contacts")
    public String contacts(Model model, @RegisteredOAuth2AuthorizedClient("google") OAuth2AuthorizedClient authorizedClient) {
        try {
            String accessToken = authorizedClient.getAccessToken().getTokenValue();
            System.out.println("✅ Access Token: " + accessToken);

            String url = "https://people.googleapis.com/v1/people/me/connections?personFields=names,emailAddresses";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            System.out.println("✅ Google API response: " + response.getBody());

            Object result = response.getBody().get("connections");
            if (result instanceof List<?> connections) {
                model.addAttribute("contacts", connections);
            } else {
                model.addAttribute("contacts", List.of());
                System.out.println("⚠️ No connections returned.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("contacts", List.of());
        }

        return "contacts";
    }


}


