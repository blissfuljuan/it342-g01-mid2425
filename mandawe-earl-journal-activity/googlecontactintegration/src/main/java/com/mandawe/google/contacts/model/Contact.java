package com.mandawe.google.contacts.model;

import java.util.List;

public class Contact {
    private String name;
    private List<String> emails;
    private List<String> phones;
    private String resourceName; // ✅ ADD THIS

    // Constructor
    public Contact(String name) {
        this.name = name;
    }

    // ✅ ADD GETTER AND SETTER for resourceName
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    // Existing getters and setters...
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
