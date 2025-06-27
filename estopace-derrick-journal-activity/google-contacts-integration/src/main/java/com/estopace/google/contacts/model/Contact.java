package com.estopace.google.contacts.model;

import java.util.List;

public class Contact {
    private String resourceName; // Google's unique identifier for the contact
    private String name;
    private String firstName;
    private String lastName;
    private List<String> emails;
    private List<String> phones;

    public Contact() {
    }

    public Contact(String name) {
        this.name = name;
    }

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

    // Getters and Setters
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        updateDisplayName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        updateDisplayName();
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

    // Helper method to get primary email
    public String getPrimaryEmail() {
        return (emails != null && !emails.isEmpty()) ? emails.get(0) : "";
    }

    // Helper method to get primary phone
    public String getPrimaryPhone() {
        return (phones != null && !phones.isEmpty()) ? phones.get(0) : "";
    }

    private void updateDisplayName() {
        this.name = (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
        this.name = this.name.trim();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "resourceName='" + resourceName + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emails=" + emails +
                ", phones=" + phones +
                '}';
    }
}