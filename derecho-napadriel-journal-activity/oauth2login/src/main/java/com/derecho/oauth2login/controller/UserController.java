package com.derecho.oauth2login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user-info")
    public String getUserInfo(@AuthenticationPrincipal OAuth2User principal, Model model) {
        model.addAttribute("user", principal.getAttributes());
        return "user-info";
    }

    @GetMapping("/")
    public String print() {
        return "home";
    }
}
