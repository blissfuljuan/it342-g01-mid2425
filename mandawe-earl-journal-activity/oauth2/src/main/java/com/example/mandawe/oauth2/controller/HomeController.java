package com.example.mandawe.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping
    @ResponseBody
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Welcome</title>
                <style>
                    body { font-family: monospace; background-color: #f4f4f4; padding: 20px; }
                    pre { background: #fff; padding: 10px; border: 1px solid #ccc; }
                </style>
            </head>
            <body>
                <pre>
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\
( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\
 \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |\\__, | / / / /
 =========|_|==============|___/=/_/_/_/
                </pre>

                <h1>Welcome to the OAuth2 Demo App</h1>
                <p>This is the home page served by <code>HomeController</code>.</p>
                <a href="/login">Login</a> | <a href="/logout">Logout</a>
            </body>
            </html>
        """;
    }

    @GetMapping("/secured")
    @ResponseBody
    public String securedHome() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Welcome</title>
                <style>
                    body { font-family: monospace; background-color: #f4f4f4; padding: 20px; }
                    pre { background: #fff; padding: 10px; border: 1px solid #ccc; }
                </style>
            </head>
            <body>
                <pre>
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\
( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\
 \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |\\__, | / / / /
 =========|_|==============|___/=/_/_/_/
                </pre>

                <h1>Welcome to the OAuth2 Demo App</h1>
                <p>This is the home page served by <code>HomeController</code>.</p>
                <a href="/login">Login</a> | <a href="/logout">Logout</a>
            </body>
            </html>
        """;
    }
}

