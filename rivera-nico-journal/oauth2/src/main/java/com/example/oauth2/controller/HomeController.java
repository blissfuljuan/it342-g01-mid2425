package com.example.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String home() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\" />\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "  <title>Otaku Haven</title>\n" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Zen+Tokyo+Zoo&display=swap\" rel=\"stylesheet\">\n" +
                "  <style>\n" +
                "    * {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "\n" +
                "    body, html {\n" +
                "      height: 100%;\n" +
                "      font-family: 'Zen Tokyo Zoo', cursive;\n" +
                "      overflow: hidden;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      display: flex;\n" +
                "      justify-content: center;\n" +
                "      align-items: center;\n" +
                "      flex-direction: column;\n" +
                "      color: #fff;\n" +
                "      text-align: center;\n" +
                "      animation: gradientShift 20s ease infinite;\n" +
                "      background: linear-gradient(-45deg, #ff9a9e, #fad0c4, #fbc2eb, #a1c4fd);\n" +
                "      background-size: 400% 400%;\n" +
                "    }\n" +
                "\n" +
                "    @keyframes gradientShift {\n" +
                "      0% {background-position: 0% 50%;}\n" +
                "      50% {background-position: 100% 50%;}\n" +
                "      100% {background-position: 0% 50%;}\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "      font-size: 4rem;\n" +
                "      text-shadow: 3px 3px 6px rgba(0,0,0,0.4);\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      font-size: 1.2rem;\n" +
                "      max-width: 600px;\n" +
                "      margin-top: 20px;\n" +
                "      text-shadow: 1px 1px 5px rgba(0,0,0,0.5);\n" +
                "    }\n" +
                "\n" +
                "    .btn {\n" +
                "      margin-top: 30px;\n" +
                "      padding: 14px 28px;\n" +
                "      font-size: 1rem;\n" +
                "      border: none;\n" +
                "      border-radius: 30px;\n" +
                "      background-color: #ff4dc4;\n" +
                "      color: white;\n" +
                "      cursor: pointer;\n" +
                "      transition: 0.3s ease;\n" +
                "    }\n" +
                "\n" +
                "    .btn:hover {\n" +
                "      background-color: #e03bb6;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Welcome to Otaku Haven</h1>\n" +
                "  <p>Step into a world where sakura petals float, epic battles unfold, and emotions run deep. Let the anime adventure begin!</p>\n" +
                "  <button class=\"btn\">Enter Now</button>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @GetMapping("/secured")
    public String secured() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\" />\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                "  <title>Otaku Haven - Secured</title>\n" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Zen+Tokyo+Zoo&display=swap\" rel=\"stylesheet\">\n" +
                "  <style>\n" +
                "    * {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "\n" +
                "    body, html {\n" +
                "      height: 100%;\n" +
                "      font-family: 'Zen Tokyo Zoo', cursive;\n" +
                "      overflow: hidden;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      display: flex;\n" +
                "      justify-content: center;\n" +
                "      align-items: center;\n" +
                "      flex-direction: column;\n" +
                "      color: #fff;\n" +
                "      text-align: center;\n" +
                "      animation: gradientShift 20s ease infinite;\n" +
                "      background: linear-gradient(-45deg, #ff9a9e, #fad0c4, #fbc2eb, #a1c4fd);\n" +
                "      background-size: 400% 400%;\n" +
                "    }\n" +
                "\n" +
                "    @keyframes gradientShift {\n" +
                "      0% {background-position: 0% 50%;}\n" +
                "      50% {background-position: 100% 50%;}\n" +
                "      100% {background-position: 0% 50%;}\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "      font-size: 4rem;\n" +
                "      text-shadow: 3px 3px 6px rgba(0,0,0,0.4);\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      font-size: 1.2rem;\n" +
                "      max-width: 600px;\n" +
                "      margin-top: 20px;\n" +
                "      text-shadow: 1px 1px 5px rgba(0,0,0,0.5);\n" +
                "    }\n" +
                "\n" +
                "    .btn {\n" +
                "      margin-top: 30px;\n" +
                "      padding: 14px 28px;\n" +
                "      font-size: 1rem;\n" +
                "      border: none;\n" +
                "      border-radius: 30px;\n" +
                "      background-color: #ff4dc4;\n" +
                "      color: white;\n" +
                "      cursor: pointer;\n" +
                "      transition: 0.3s ease;\n" +
                "    }\n" +
                "\n" +
                "    .btn:hover {\n" +
                "      background-color: #e03bb6;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Welcome to Otaku Haven</h1>\n" +
                "  <p>Step into a world where sakura petals float, epic battles unfold, and emotions run deep. Let the anime adventure begin!</p>\n" +
                "  <button class=\"btn\">Enter Now</button>\n" +
                "</body>\n" +
                "</html>\n";

    }
}