package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String home(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>My Simple Homepage</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      margin: 40px;\n" +
                "      background-color: #f4f4f4;\n" +
                "      color: #333;\n" +
                "    }\n" +
                "    header {\n" +
                "      background-color: #4CAF50;\n" +
                "      color: white;\n" +
                "      padding: 20px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    main {\n" +
                "      margin-top: 20px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <header>\n" +
                "    <h1>Welcome to My Homepage</h1>\n" +
                "  </header>\n" +
                "\n" +
                "  <main>\n" +
                "    <p>Hello! This is a simple HTML homepage created as an example.</p>\n" +
                "    <p>This is not secured.</p>\n" +
                "  </main>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }

    @GetMapping("/secured")
    public String secured(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>My Simple Homepage</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      margin: 40px;\n" +
                "      background-color: #f4f4f4;\n" +
                "      color: #333;\n" +
                "    }\n" +
                "    header {\n" +
                "      background-color: #4CAF50;\n" +
                "      color: white;\n" +
                "      padding: 20px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    main {\n" +
                "      margin-top: 20px;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <header>\n" +
                "    <h1>Welcome to My Homepage</h1>\n" +
                "  </header>\n" +
                "\n" +
                "  <main>\n" +
                "    <p>Hello! This is a simple HTML homepage created as an example.</p>\n" +
                "    <p>This is currently secured.</p>\n" +
                "  </main>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
}