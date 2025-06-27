# 📖 Google Contacts Manager (Spring Boot + Thymeleaf + OAuth2)

This is a Spring Boot web application that integrates with the **Google People API** to allow users to **view, add, edit, and delete** their Google contacts. It uses **OAuth2 authentication**, **Thymeleaf templates**, and supports **multiple email addresses and phone numbers per contact (up to 3 each).**

---

## 🚀 Features

- ✅ Google Sign-in via OAuth2
- ✅ List all Google contacts (names, emails, phone numbers)
- ✅ Add new contact
- ✅ Edit contact details
- ✅ Delete contact
- ✅ Beautiful Bootstrap-based UI

---

## 🛠️ Tech Stack

- **Spring Boot**
- **Spring Security (OAuth2 Client)**
- **Google People API**
- **Thymeleaf**
- **Bootstrap 5**
- **Java 17+**

---

## 📋 Setup Instructions

1. **Clone the project:**

   ```bash
   git clone https://github.com/KianaDelMar/it342-g01-mid2425
   cd delmar-kiana-journal-activity
   cd googlecontacts

2. **Configure your application.properties:**
   
  Replace the placeholders with your Google API credentials.

3. **Enable People API:**
   
    Visit: https://console.cloud.google.com/
    Create a project and enable the People API.
    Add http://localhost:8080/login/oauth2/code/google as an authorized redirect URI.

4. Run the app:
    ```bash
    ./mvnw spring-boot:run
    
5. **Access the app:**
    Navigate to http://localhost:8080


