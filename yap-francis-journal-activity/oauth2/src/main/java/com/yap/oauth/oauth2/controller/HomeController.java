package com.yap.oauth.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Hello World</title>
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #f0f0f0; text-align: center; padding-top: 50px; }
                        h1 { color: #333; }
                    </style>
                </head>
                <body>
                    <h1>Hello World</h1>
                </body>
                </html>
                """;
    }

    @GetMapping("/secured")
    public String secured() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Secured Page</title>
                    <style>
                        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #e0f7fa; text-align: center; padding-top: 50px; }
                        h1 { color: #00796b; }
                    </style>
                </head>
                <body>
                    <h1>Hello World</h1>
                    <h1>This is a secured page</h1>
                </body>
                </html>
                """;
    }
}