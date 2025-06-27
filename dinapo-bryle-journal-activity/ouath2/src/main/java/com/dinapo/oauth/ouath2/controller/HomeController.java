package com.dinapo.oauth.ouath2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping
    @ResponseBody
    public String home() {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "    <meta charset='UTF-8' />" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1' />" +
                "    <title>Starcorn</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: 'Arial', sans-serif;" +
                "            background-color: #1c1c1c;" +
                "            color: white;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            text-align: center;" +
                "        }" +
                "        .header {" +
                "            background-color: #f7d500;" +
                "            color: black;" +
                "            padding: 20px;" +
                "            font-size: 2.5em;" +
                "            font-weight: bold;" +
                "            text-transform: uppercase;" +
                "            letter-spacing: 1px;" +
                "            box-shadow: 0 2px 5px rgba(0,0,0,0.3);" +
                "        }" +
                "        .container {" +
                "            margin-top: 80px;" +
                "        }" +
                "        h1 {" +
                "            font-size: 3em;" +
                "            color: #f7d500;" +
                "            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);" +
                "        }" +
                "        p {" +
                "            font-size: 1.2em;" +
                "            margin-bottom: 30px;" +
                "        }" +
                "        .button {" +
                "            background-color: #f7d500;" +
                "            color: black;" +
                "            border: none;" +
                "            border-radius: 8px;" +
                "            padding: 15px 30px;" +
                "            margin: 10px;" +
                "            font-size: 1.2em;" +
                "            font-weight: bold;" +
                "            cursor: pointer;" +
                "            box-shadow: 0 4px 8px rgba(0,0,0,0.2);" +
                "            transition: all 0.3s ease;" +
                "        }" +
                "        .button:hover {" +
                "            background-color: #f0c600;" +
                "            box-shadow: 0 6px 10px rgba(0,0,0,0.3);" +
                "        }" +
                "        .arrow {" +
                "            margin: 10px;" +
                "            font-size: 2em;" +
                "            color: #f7d500;" +
                "            cursor: pointer;" +
                "            transition: transform 0.3s ease;" +
                "        }" +
                "        .arrow:hover {" +
                "            transform: translateX(5px);" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='header'>Starcorn</div>" +
                "    <div class='container'>" +
                "        <h1>Welcome to Starcorn</h1>" +
                "        <p>Explore your world of stars with the best social experience.</p>" +
                "        <button class='button' onclick=\"alert('Logged out!')\">Log Out</button>" +
                "        <button class='button' onclick=\"alert('Fuck Yeah!')\">Fuck Yeah</button>" +
                "        <button class='button' onclick=\"alert('Like it!')\">Like</button>" +
                "        <button class='button' onclick=\"alert('Share it!')\">Share</button>" +
                "        <div>" +
                "            <span class='arrow' onclick=\"alert('Left Arrow clicked!')\">←</span>" +
                "            <span class='arrow' onclick=\"alert('Right Arrow clicked!')\">→</span>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }

    // Renamed the second method to avoid name conflict
    @GetMapping("/secured")
    @ResponseBody
    public String yeah() {
        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "    <meta charset='UTF-8' />" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1' />" +
                "    <title>Starcorn</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: 'Arial', sans-serif;" +
                "            background-color: #1c1c1c;" +
                "            color: white;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            text-align: center;" +
                "        }" +
                "        .header {" +
                "            background-color: #f7d500;" +
                "            color: black;" +
                "            padding: 20px;" +
                "            font-size: 2.5em;" +
                "            font-weight: bold;" +
                "            text-transform: uppercase;" +
                "            letter-spacing: 1px;" +
                "            box-shadow: 0 2px 5px rgba(0,0,0,0.3);" +
                "        }" +
                "        .container {" +
                "            margin-top: 80px;" +
                "        }" +
                "        h1 {" +
                "            font-size: 3em;" +
                "            color: #f7d500;" +
                "            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);" +
                "        }" +
                "        p {" +
                "            font-size: 1.2em;" +
                "            margin-bottom: 30px;" +
                "        }" +
                "        .button {" +
                "            background-color: #f7d500;" +
                "            color: black;" +
                "            border: none;" +
                "            border-radius: 8px;" +
                "            padding: 15px 30px;" +
                "            margin: 10px;" +
                "            font-size: 1.2em;" +
                "            font-weight: bold;" +
                "            cursor: pointer;" +
                "            box-shadow: 0 4px 8px rgba(0,0,0,0.2);" +
                "            transition: all 0.3s ease;" +
                "        }" +
                "        .button:hover {" +
                "            background-color: #f0c600;" +
                "            box-shadow: 0 6px 10px rgba(0,0,0,0.3);" +
                "        }" +
                "        .arrow {" +
                "            margin: 10px;" +
                "            font-size: 2em;" +
                "            color: #f7d500;" +
                "            cursor: pointer;" +
                "            transition: transform 0.3s ease;" +
                "        }" +
                "        .arrow:hover {" +
                "            transform: translateX(5px);" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='header'>Starcorn</div>" +
                "    <div class='container'>" +
                "        <h1>Welcome to Starcorn</h1>" +
                "        <p>Explore your world of stars with the best social experience.</p>" +
                "        <button class='button' onclick=\"alert('Logged out!')\">Log Out</button>" +
                "        <button class='button' onclick=\"alert('Fuck Yeah!')\">Fuck Yeah</button>" +
                "        <button class='button' onclick=\"alert('Like it!')\">Like</button>" +
                "        <button class='button' onclick=\"alert('Share it!')\">Share</button>" +
                "        <div>" +
                "            <span class='arrow' onclick=\"alert('Left Arrow clicked!')\">←</span>" +
                "            <span class='arrow' onclick=\"alert('Right Arrow clicked!')\">→</span>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
