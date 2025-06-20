package com.people.api.google.controller;

import com.people.api.google.model.ContactDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GoogleOAuthController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/contacts")
    public String getContactData(OAuth2AuthenticationToken authentication, Model model) {
        if (authentication == null) {
            return "redirect:/";
        }

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());

        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        String peopleApiUrl = "https://people.googleapis.com/v1/people/me/connections"
                + "?personFields=names,emailAddresses,phoneNumbers";

        ResponseEntity<String> response = restTemplate.exchange(peopleApiUrl, HttpMethod.GET, entity, String.class);

        // Parse JSON response into List<ContactDTO>
        List<ContactDTO> contactList = new ArrayList<>();
        JSONObject json = new JSONObject(response.getBody());
        JSONArray connections = json.optJSONArray("connections");

        if (connections != null) {
            for (int i = 0; i < connections.length(); i++) {
                JSONObject person = connections.getJSONObject(i);
                ContactDTO contact = new ContactDTO();
                contact.setResourceName(person.optString("resourceName"));

                if (person.has("names"))
                    contact.setName(person.getJSONArray("names").getJSONObject(0).optString("displayName"));

                if (person.has("emailAddresses"))
                    contact.setEmail(person.getJSONArray("emailAddresses").getJSONObject(0).optString("value"));

                if (person.has("phoneNumbers"))
                    contact.setPhone(person.getJSONArray("phoneNumbers").getJSONObject(0).optString("value"));

                contactList.add(contact);
            }
        }

        model.addAttribute("parsedContacts", contactList);
        return "contacts";
    }

    @PostMapping("/contacts/create")
    public String createContact(@RequestParam String name, @RequestParam String email,
                                @RequestParam String phone, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();

        String createUrl = "https://people.googleapis.com/v1/people:createContact";

        String requestBody = """
        {
          "names": [{"givenName": "%s"}],
          "emailAddresses": [{"value": "%s"}],
          "phoneNumbers": [{"value": "%s"}]
        }
        """.formatted(name, email, phone);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(createUrl, entity, String.class);

        return "redirect:/contacts";
    }

    @GetMapping("/contacts/delete")
    public String deleteContact(@RequestParam String resourceName, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();

        String deleteUrl = "https://people.googleapis.com/v1/" + resourceName + ":deleteContact";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, Void.class);

        return "redirect:/contacts";
    }

    @GetMapping("/contacts/edit")
    public String editContact(@RequestParam String resourceName, Model model) {
        model.addAttribute("resourceName", resourceName);
        return "edit_contact";
    }

    @PostMapping("/contacts/update")
    public String updateContact(@RequestParam String resourceName, @RequestParam String name,
                                @RequestParam String email, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();

        String updateUrl = "https://people.googleapis.com/v1/" + resourceName + ":updateContact?updatePersonFields=names,emailAddresses";

        String requestBody = """
        {
          "names": [{"givenName": "%s"}],
          "emailAddresses": [{"value": "%s"}]
        }
        """.formatted(name, email);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.exchange(updateUrl, HttpMethod.PATCH, entity, String.class);

        return "redirect:/contacts";
    }
}
