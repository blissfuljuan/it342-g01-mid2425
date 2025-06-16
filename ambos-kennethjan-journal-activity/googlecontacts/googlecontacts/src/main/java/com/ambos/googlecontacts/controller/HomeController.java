package com.ambos.googlecontacts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Simple Homepage</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Hello World</h1>\n" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping("/secured")
    public String secured(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <title>Home - Secured</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Hello World</h1>\n" +
                "    <h1>This is a secured page</h1>\n" +
                "</body>\n" +
                "</html>";
    }
}
