# 📖 Google Contacts Manager

A modern web application built with Spring Boot that seamlessly connects to your Google Contacts using the Google People API. Effortlessly view, add, update, and remove your contacts—all in a beautiful, secure interface. The app leverages OAuth2 for authentication, Thymeleaf for dynamic pages, and supports up to 3 emails and phone numbers per contact.

---

## 🚀 What You Can Do

- 🔐 Sign in securely with your Google account (OAuth2)
- 📋 Browse all your Google contacts (names, emails, phone numbers)
- ➕ Add new contacts
- ✏️ Edit existing contact details
- 🗑️ Remove contacts you no longer need
- 🎨 Enjoy a polished, responsive UI powered by Bootstrap 5

---

## 🛠️ Technologies Used

- Java 17+
- Spring Boot & Spring Security (OAuth2 Client)
- Google People API
- Thymeleaf
- Bootstrap 5

---

## 🏁 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/StraterFoxx/it342-g01-mid2425
cd yap-francis-journal-activity
cd google-contancts-integration
```

### 2. Set up your Google API credentials

- Open `src/main/resources/application.properties`.
- Fill in your Google API client ID and secret where indicated.

### 3. Enable the Google People API

- Go to [Google Cloud Console](https://console.cloud.google.com/)
- Create a new project (or select an existing one)
- Enable the People API for your project
- Add `http://localhost:8080/login/oauth2/code/google` as an authorized redirect URI

### 4. Launch the application

```bash
./mvnw spring-boot:run
```

### 5. Open your browser

Visit [http://localhost:8080](http://localhost:8080) to start managing your Google Contacts!

---

Enjoy a smarter, faster way to manage your contacts with Google Contacts Manager! 🚀