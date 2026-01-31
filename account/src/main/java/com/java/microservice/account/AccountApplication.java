package com.java.microservice.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
// @ComponentScans(@ComponentScan("com.java.microservice.account.controller"))
// @EnableJpaRepositories("com.java.microservice.account.repository")
// @EntityScan("com.java.microservice.account.entity")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
	info = @Info(
		title = "Account microservice REST API Documentation",
		description = "Account microservice REST API Documentation",
		version = "v1",
		contact = @Contact(
			name = "Aditya Kumar",
			email = "shahaditya292@gmail.com",
			url = "http://localhost:8080"
		),
		license = @License(
			name = "Apache 2.0",
			url = "http://localhost:8080"
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "Account microservice REST API Documentation",
		url = "http://localhost:8080/swagger-ui/index.html"
	)
)
public class AccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

}
