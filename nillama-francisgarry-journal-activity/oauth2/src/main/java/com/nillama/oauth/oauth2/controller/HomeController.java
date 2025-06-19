package com.nillama.oauth.oauth2.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <title>Welcome</title>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; text-align: center; margin-top: 20%; background-color: #f7f7f7; }" +
                "        h1 { color: #333; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <h1>Welcome</h1>" +
                "</body>" +
                "</html>";
    }
    @GetMapping("/secured")
    public String secured() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <title>Welcome</title>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; text-align: center; margin-top: 20%; background-color: #f7f7f7; }" +
                "        h1 { color: #333; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <h1>Welcome to Secured</h1>" +
                "</body>" +
                "</html>";
    }
}
