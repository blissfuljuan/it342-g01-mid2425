package com.estopace.oauth.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    // Home page
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>OAuth2 Authentication</title>"
                + "<style>"
                + "    /* Resetting defaults */"
                + "    * { margin: 0; padding: 0; box-sizing: border-box; }"
                + "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f1f1f1; color: #333; line-height: 1.6; }"
                + "    h1, h2 { font-weight: 600; }"
                + "    h1 { font-size: 2.5rem; color: #333; margin-bottom: 10px; }"
                + "    h2 { color: #4CAF50; margin-top: 20px; font-size: 1.8rem; }"

                + "    /* Page layout */"
                + "    header { background-color: #4CAF50; color: white; padding: 40px 0; text-align: center; }"
                + "    main { max-width: 800px; margin: 40px auto; padding: 30px; background: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }"
                + "    footer { text-align: center; padding: 20px; background-color: #333; color: white; position: relative; bottom: 0; width: 100%; }"

                + "    /* Navigation styles */"
                + "    nav { text-align: center; background-color: #333; padding: 10px 0; margin: 0 auto; width: 100%; }"
                + "    nav a { color: white; text-decoration: none; font-size: 1.2rem; margin: 0 20px; padding: 10px 15px; display: inline-block; border-radius: 5px; }"
                + "    nav a:hover { background-color: #45a049; }"

                + "    /* Button styles */"
                + "    .btn { padding: 10px 20px; background-color: #4CAF50; color: white; text-align: center; border-radius: 5px; text-decoration: none; font-weight: bold; display: inline-block; transition: background-color 0.3s ease; }"
                + "    .btn:hover { background-color: #45a049; }"

                + "    /* Responsive Design for Mobile */"
                + "    @media screen and (max-width: 768px) {"
                + "        body { padding: 10px; }"
                + "        main { padding: 20px; }"
                + "        h1 { font-size: 2rem; }"
                + "        h2 { font-size: 1.6rem; }"
                + "        nav a { font-size: 1rem; margin: 0 10px; }"
                + "    }"
                + "</style>"
                + "</head>"
                + "<body>"

                + "<header>"
                + "    <h1>Welcome to the OAuth2 Authentication Demo</h1>"
                + "    <p style=\"font-size: 1.1rem; margin-top: 10px;\">Experience the power of OAuth2 in action!</p>"
                + "</header>"

                + "<nav>"
                + "    <a href=\"/login\" class=\"btn\">Login</a>"
                + "    <a href=\"/logout\" class=\"btn\">Logout</a>"
                + "</nav>"

                + "<main>"
                + "    <h2>Get Started</h2>"
                + "    <p>This is a demo of OAuth2 authentication. To proceed, click the <strong>Login</strong> button above and authenticate using your credentials.</p>"
                + "    <p>If you're already logged in, feel free to log out using the <strong>Logout</strong> button.</p>"
                + "    <p>OAuth2 ensures that your authentication process is both secure and efficient, giving you full control over your data.</p>"
                + "</main>"

                + "<footer>"
                + "    <p>&copy; 2025 OAuth2 Authentication Demo | All Rights Reserved</p>"
                + "</footer>"

                + "</body>"
                + "</html>";
    }

    // Secured page
    @GetMapping("/secured")
    @ResponseBody
    public String secured() {
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Secured Page - OAuth2 Authentication</title>"
                + "<style>"
                + "    * { margin: 0; padding: 0; box-sizing: border-box; }"
                + "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f1f1f1; color: #333; line-height: 1.6; }"
                + "    h1, h2 { font-weight: 600; }"
                + "    h1 { font-size: 2.5rem; color: #333; margin-bottom: 10px; }"
                + "    h2 { color: #ff6347; margin-top: 20px; font-size: 1.8rem; }"

                + "    /* Page layout */"
                + "    header { background-color: #ff6347; color: white; padding: 40px 0; text-align: center; }"
                + "    main { max-width: 800px; margin: 40px auto; padding: 30px; background: #fff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }"
                + "    footer { text-align: center; padding: 20px; background-color: #333; color: white; position: relative; bottom: 0; width: 100%; }"

                + "    /* Navigation styles */"
                + "    nav { text-align: center; background-color: #333; padding: 10px 0; margin: 0 auto; width: 100%; }"
                + "    nav a { color: white; text-decoration: none; font-size: 1.2rem; margin: 0 20px; padding: 10px 15px; display: inline-block; border-radius: 5px; }"
                + "    nav a:hover { background-color: #45a049; }"

                + "    /* Button styles */"
                + "    .btn { padding: 10px 20px; background-color: #4CAF50; color: white; text-align: center; border-radius: 5px; text-decoration: none; font-weight: bold; display: inline-block; transition: background-color 0.3s ease; }"
                + "    .btn:hover { background-color: #45a049; }"

                + "    /* Responsive Design for Mobile */"
                + "    @media screen and (max-width: 768px) {"
                + "        body { padding: 10px; }"
                + "        main { padding: 20px; }"
                + "        h1 { font-size: 2rem; }"
                + "        h2 { font-size: 1.6rem; }"
                + "        nav a { font-size: 1rem; margin: 0 10px; }"
                + "    }"
                + "</style>"
                + "</head>"
                + "<body>"

                + "<header>"
                + "    <h1>Welcome to the Secured OAuth2 Page</h1>"
                + "    <p style=\"font-size: 1.1rem; margin-top: 10px;\">This page is only accessible for authenticated users.</p>"
                + "</header>"

                + "<nav>"
                + "    <a href=\"/logout\" class=\"btn\">Logout</a>"
                + "</nav>"

                + "<main>"
                + "    <h2>Congratulations!</h2>"
                + "    <p>You have successfully authenticated and are now viewing the secured page.</p>"
                + "    <p>Feel free to log out using the button above.</p>"
                + "</main>"

                + "<footer>"
                + "    <p>&copy; 2025 OAuth2 Authentication Demo | All Rights Reserved</p>"
                + "</footer>"

                + "</body>"
                + "</html>";
    }
}