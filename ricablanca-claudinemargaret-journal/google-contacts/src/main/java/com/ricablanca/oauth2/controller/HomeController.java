package com.ricablanca.oauth2.controller;

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
                "  <title>TechSpace Homepage</title>\n" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap\" rel=\"stylesheet\">\n" +
                "  <style>\n" +
                "    * {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      font-family: 'Roboto', sans-serif;\n" +
                "      background: linear-gradient(135deg, #1f1c2c, #928dab);\n" +
                "      color: #f4f4f4;\n" +
                "    }\n" +
                "\n" +
                "    header {\n" +
                "      text-align: center;\n" +
                "      padding: 60px 20px 30px;\n" +
                "      background: rgba(0, 0, 0, 0.6);\n" +
                "    }\n" +
                "\n" +
                "    header h1 {\n" +
                "      font-size: 3em;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "\n" +
                "    header p {\n" +
                "      font-size: 1.2em;\n" +
                "      color: #ddd;\n" +
                "    }\n" +
                "\n" +
                "    nav {\n" +
                "      display: flex;\n" +
                "      justify-content: center;\n" +
                "      background: #111;\n" +
                "      padding: 15px 0;\n" +
                "    }\n" +
                "\n" +
                "    nav a {\n" +
                "      color: #f4f4f4;\n" +
                "      text-decoration: none;\n" +
                "      margin: 0 20px;\n" +
                "      font-weight: 500;\n" +
                "      transition: color 0.3s ease;\n" +
                "    }\n" +
                "\n" +
                "    nav a:hover {\n" +
                "      color: #00eaff;\n" +
                "    }\n" +
                "\n" +
                "    main {\n" +
                "      max-width: 900px;\n" +
                "      margin: 40px auto;\n" +
                "      padding: 20px;\n" +
                "      background: rgba(255, 255, 255, 0.1);\n" +
                "      border-radius: 15px;\n" +
                "      box-shadow: 0 0 15px rgba(0,0,0,0.3);\n" +
                "    }\n" +
                "\n" +
                "    main h2 {\n" +
                "      font-size: 2em;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    main p {\n" +
                "      font-size: 1.1em;\n" +
                "      line-height: 1.6;\n" +
                "      color: #e0e0e0;\n" +
                "    }\n" +
                "\n" +
                "    .card {\n" +
                "      background: rgba(255, 255, 255, 0.05);\n" +
                "      padding: 20px;\n" +
                "      margin-top: 30px;\n" +
                "      border-left: 5px solid #00eaff;\n" +
                "      border-radius: 10px;\n" +
                "    }\n" +
                "\n" +
                "    footer {\n" +
                "      text-align: center;\n" +
                "      padding: 20px;\n" +
                "      margin-top: 60px;\n" +
                "      background: #000;\n" +
                "      font-size: 0.9em;\n" +
                "      color: #aaa;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <header>\n" +
                "    <h1>TechSpace</h1>\n" +
                "    <p>Where Innovation Meets the Web</p>\n" +
                "  </header>\n" +
                "\n" +
                "  <nav>\n" +
                "    <a href=\"#\">Home</a>\n" +
                "    <a href=\"#\">Projects</a>\n" +
                "    <a href=\"#\">Tutorials</a>\n" +
                "    <a href=\"#\">Contact</a>\n" +
                "  </nav>\n" +
                "\n" +
                "  <main>\n" +
                "    <h2>Welcome to TechSpace</h2>\n" +
                "    <p>This homepage is your starting point into the world of technology and web development. Learn the foundations of HTML, CSS, and beyond with simple tutorials and project ideas.</p>\n" +
                "    \n" +
                "    <div class=\"card\">\n" +
                "      <h3>\uD83D\uDCA1 Why Learn Web Development?</h3>\n" +
                "      <p>Web development is one of the most in-demand skills in tech. With just HTML and CSS, you can start creating amazing websites and digital tools.</p>\n" +
                "    </div>\n" +
                "  </main>\n" +
                "\n" +
                "  <footer>\n" +
                "    &copy; 2025 TechSpace | Designed with \uD83D\uDCBB and ☕\n" +
                "  </footer>\n" +
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
                "  <title>TechSpace Homepage</title>\n" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@300;700&display=swap\" rel=\"stylesheet\">\n" +
                "  <style>\n" +
                "    * {\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      font-family: 'Roboto', sans-serif;\n" +
                "      background: linear-gradient(135deg, #1f1c2c, #928dab);\n" +
                "      color: #f4f4f4;\n" +
                "    }\n" +
                "\n" +
                "    header {\n" +
                "      text-align: center;\n" +
                "      padding: 60px 20px 30px;\n" +
                "      background: rgba(0, 0, 0, 0.6);\n" +
                "    }\n" +
                "\n" +
                "    header h1 {\n" +
                "      font-size: 3em;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "\n" +
                "    header p {\n" +
                "      font-size: 1.2em;\n" +
                "      color: #ddd;\n" +
                "    }\n" +
                "\n" +
                "    nav {\n" +
                "      display: flex;\n" +
                "      justify-content: center;\n" +
                "      background: #111;\n" +
                "      padding: 15px 0;\n" +
                "    }\n" +
                "\n" +
                "    nav a {\n" +
                "      color: #f4f4f4;\n" +
                "      text-decoration: none;\n" +
                "      margin: 0 20px;\n" +
                "      font-weight: 500;\n" +
                "      transition: color 0.3s ease;\n" +
                "    }\n" +
                "\n" +
                "    nav a:hover {\n" +
                "      color: #00eaff;\n" +
                "    }\n" +
                "\n" +
                "    main {\n" +
                "      max-width: 900px;\n" +
                "      margin: 40px auto;\n" +
                "      padding: 20px;\n" +
                "      background: rgba(255, 255, 255, 0.1);\n" +
                "      border-radius: 15px;\n" +
                "      box-shadow: 0 0 15px rgba(0,0,0,0.3);\n" +
                "    }\n" +
                "\n" +
                "    main h2 {\n" +
                "      font-size: 2em;\n" +
                "      margin-bottom: 20px;\n" +
                "    }\n" +
                "\n" +
                "    main p {\n" +
                "      font-size: 1.1em;\n" +
                "      line-height: 1.6;\n" +
                "      color: #e0e0e0;\n" +
                "    }\n" +
                "\n" +
                "    .card {\n" +
                "      background: rgba(255, 255, 255, 0.05);\n" +
                "      padding: 20px;\n" +
                "      margin-top: 30px;\n" +
                "      border-left: 5px solid #00eaff;\n" +
                "      border-radius: 10px;\n" +
                "    }\n" +
                "\n" +
                "    footer {\n" +
                "      text-align: center;\n" +
                "      padding: 20px;\n" +
                "      margin-top: 60px;\n" +
                "      background: #000;\n" +
                "      font-size: 0.9em;\n" +
                "      color: #aaa;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <header>\n" +
                "    <h1>TechSpace</h1>\n" +
                "    <p>Where Innovation Meets the Web</p>\n" +
                "  </header>\n" +
                "\n" +
                "  <nav>\n" +
                "    <a href=\"#\">Home</a>\n" +
                "    <a href=\"#\">Projects</a>\n" +
                "    <a href=\"#\">Tutorials</a>\n" +
                "    <a href=\"#\">Contact</a>\n" +
                "  </nav>\n" +
                "\n" +
                "  <main>\n" +
                "    <h2>Welcome to TechSpace</h2>\n" +
                "    <p>This is a Secured page.</p>\n" +
                "    \n" +
                "    <div class=\"card\">\n" +
                "      <h3>\uD83D\uDCA1 Why Learn Web Development?</h3>\n" +
                "      <p>Web development is one of the most in-demand skills in tech. With just HTML and CSS, you can start creating amazing websites and digital tools.</p>\n" +
                "    </div>\n" +
                "  </main>\n" +
                "\n" +
                "  <footer>\n" +
                "    &copy; 2025 TechSpace | Designed with \uD83D\uDCBB and ☕\n" +
                "  </footer>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
}
