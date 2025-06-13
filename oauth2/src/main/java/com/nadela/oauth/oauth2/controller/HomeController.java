package com.nadela.oauth.oauth2.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/secured")
    public String home(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "  <title>Home Page - Secured</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      display: flex;\n" +
                "      justify-content: center;\n" +
                "      align-items: center;\n" +
                "      height: 100vh;\n" +
                "      margin: 0;\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      background-color: #f0f0f0;\n" +
                "    }\n" +
                "    h1 {\n" +
                "      color: #333;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Hello World! This is a Secured page.</h1>\n" +
                "</body>\n" +
                "</html>\n";

    }
}
