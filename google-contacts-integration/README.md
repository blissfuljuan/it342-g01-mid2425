# Google Contacts Integration - Steps

## 1. Project Setup
- Spring Boot project using Maven.
- Dependencies: Spring Boot Web, Security, OAuth2 Client, Google People API.

## 2. Configuration
- `application.properties` contains OAuth2 credentials for Google and GitHub.
- Scopes set for Google Contacts and user info.

## 3. Authentication
- OAuth2 login enabled for Google and GitHub.
- Login page dynamically lists available OAuth2 providers.

## 4. Main Features
- After login, users can:
  - View their Google Contacts (`/contacts`).
  - View their user info (`/user-info`).

## 5. Controllers
- `LoginController`: Handles login and provider URLs.
- `ContactsController`: Fetches and displays Google Contacts.
- `UserController`: Displays authenticated user info.

## 6. Running the App
- Build with `./mvnw clean package`.
- Run with `./mvnw spring-boot:run`.
- Visit `http://localhost:8080` and log in with Google.
