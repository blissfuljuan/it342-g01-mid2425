package com.magpatoc.google.contacts.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {

    private String name;
    private List<String> emails;
    private List<String> phones;

    public Contact() {
        this.emails = new ArrayList<>();
        this.phones = new ArrayList<>();
    }

    public Contact(String name) {
        this.name = name;
        this.emails = new ArrayList<>();
        this.phones = new ArrayList<>();
    }

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
