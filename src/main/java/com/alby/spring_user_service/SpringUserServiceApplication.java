package com.alby.spring_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringUserServiceApplication.class, args);
	}

}
