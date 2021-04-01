package com.innogrid.oauth2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Oauth2Application {

	public static final String PROPERTIES = "spring.config.location=classpath:/google.yaml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(Oauth2Application.class)
				.properties(PROPERTIES)
				.run(args);
	}

}
