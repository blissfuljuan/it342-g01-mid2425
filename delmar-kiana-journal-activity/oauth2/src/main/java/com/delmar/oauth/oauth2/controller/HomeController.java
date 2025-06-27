package com.delmar.oauth.oauth2.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {
    @GetMapping
    public String home() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Homepage</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Hello, World</h1>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @GetMapping("/secured")
    public String secured() {
        return  "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Homepage - Secured</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Hello, World! This is a secured page.</h1>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
