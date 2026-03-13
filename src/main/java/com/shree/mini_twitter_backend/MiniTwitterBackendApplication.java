package com.shree.mini_twitter_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class MiniTwitterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniTwitterBackendApplication.class, args);
	}
}
