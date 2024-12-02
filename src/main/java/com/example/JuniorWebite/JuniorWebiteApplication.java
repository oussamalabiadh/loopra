package com.example.JuniorWebite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class JuniorWebiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuniorWebiteApplication.class, args);
	}

}

