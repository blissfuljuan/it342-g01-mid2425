package com.ambos.googlecontacts.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @GetMapping("/user-info")
    public String userInfo(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String fullName = (String) oAuth2User.getAttribute("name");
        String givenName = (String) oAuth2User.getAttribute("given_name");
        String familyName = (String) oAuth2User.getAttribute("family_name");
        String email = (String) oAuth2User.getAttribute("email");

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>User Info</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h2>User Profile</h2>\n" +
                "    <p><strong>Full Name:</strong> " + fullName + "</p>\n" +
                "    <p><strong>Given Name:</strong> " + givenName + "</p>\n" +
                "    <p><strong>Family Name:</strong> " + familyName + "</p>\n" +
                "    <p><strong>Email:</strong> " + email + "</p>\n" +
                "    <form action=\"/logout\" method=\"post\">\n" +
                "        <button type=\"submit\">contacts</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>";
    }
}
