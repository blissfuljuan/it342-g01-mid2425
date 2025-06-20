package com.rigodon.oauth2.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "    <title>Pornhub- Home</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #000;\n" +
                "            color: #fff;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-size: 3rem;\n" +
                "            color: #ff9900;\n" +
                "        }\n" +
                "        p {\n" +
                "            font-size: 1.2rem;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        a.button {\n" +
                "            padding: 12px 24px;\n" +
                "            background-color: #ff9900;\n" +
                "            color: #000;\n" +
                "            text-decoration: none;\n" +
                "            font-weight: bold;\n" +
                "            border-radius: 4px;\n" +
                "            transition: background 0.3s ease;\n" +
                "        }\n" +
                "        a.button:hover {\n" +
                "            background-color: #e68a00;\n" +
                "        }\n" +
                "        footer {\n" +
                "            position: fixed;\n" +
                "            bottom: 10px;\n" +
                "            font-size: 0.9rem;\n" +
                "            color: #999;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Pornhub</h1>\n" +
                "    <p>Secure. Fast. Reliable Authentication for Modern Applications.</p>\n" +
                "    <a href='/login' class='button'>Login</a>\n" +
                "    <footer>&copy; 2025 Rigodon Auth</footer>\n" +
                "</body>\n" +
                "</html>";

    }

    @GetMapping("/secured")
    public String sercured() {
        return "<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" +
                "    <title>Pornhub- Home</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #000;\n" +
                "            color: #fff;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-size: 3rem;\n" +
                "            color: #ff9900;\n" +
                "        }\n" +
                "        p {\n" +
                "            font-size: 1.2rem;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        a.button {\n" +
                "            padding: 12px 24px;\n" +
                "            background-color: #ff9900;\n" +
                "            color: #000;\n" +
                "            text-decoration: none;\n" +
                "            font-weight: bold;\n" +
                "            border-radius: 4px;\n" +
                "            transition: background 0.3s ease;\n" +
                "        }\n" +
                "        a.button:hover {\n" +
                "            background-color: #e68a00;\n" +
                "        }\n" +
                "        footer {\n" +
                "            position: fixed;\n" +
                "            bottom: 10px;\n" +
                "            font-size: 0.9rem;\n" +
                "            color: #999;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Pornhub</h1>\n" +
                "    <p>Secure. Fast. Reliable Authentication for Modern Applications.</p>\n" +
                "    <a href='/login' class='button'>Login</a>\n" +
                "    <footer>&copy; 2025 Rigodon Auth</footer>\n" +
                "</body>\n" +
                "</html>";
    }

}
