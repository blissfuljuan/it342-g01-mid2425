package com.mandawe.google.contacts.model;

import java.util.List;

public class Contact {
    private String name;
    private List<String> emails;
    private List<String> phones;
    private String resourceName;
    private String etag; // ✅ Added for update operations

    // Constructor
    public Contact(String name) {
        this.name = name;
    }

    // ✅ Getter and Setter for etag
    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    // Getter and Setter for resourceName
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    // Other Getters and Setters
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
