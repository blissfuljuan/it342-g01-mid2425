package com.magpatoc.google.contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleContactsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleContactsApplication.class, args);
		System.out.println("✅ Google Contacts Application started.");
	}
}
