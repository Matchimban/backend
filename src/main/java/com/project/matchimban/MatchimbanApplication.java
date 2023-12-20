package com.project.matchimban;

//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(servers = {@Server(url = "https://15.164.94.57:8080", description = "Default server URL")})
@SpringBootApplication
@EnableJpaAuditing
public class MatchimbanApplication {

	public static void main(String[] args) {



		SpringApplication.run(MatchimbanApplication.class, args);
	}

}
