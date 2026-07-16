package com.chapt_gpt_clone.chaptgpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
public class ChaptgptApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaptgptApplication.class, args);
	}

}
