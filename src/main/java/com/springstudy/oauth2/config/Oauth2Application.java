package com.springstudy.oauth2.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Oauth2Application {


	public static void main(String[] args) {
		SpringApplication.run(Oauth2Application.class, args);

	}

}
