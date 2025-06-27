package com.dinapo.google.contacts.controller;

import com.dinapo.google.contacts.utils.Utils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class UserController {

    @GetMapping               // GET /
    public String index() {
        return "index";
    }

    @GetMapping("/user-info") // GET /user-info
    public String userInfo(@AuthenticationPrincipal OAuth2User principal,
                           Model model) {

        System.out.println("UserController: /user-info hit");

        if (principal != null) {
            model.addAttribute("user", Utils.OAuth2UserToUser(principal));
        } else {
            model.addAttribute("user", Collections.emptyMap());
        }
        return "user-info";   // templates/user-info.html
    }
}
