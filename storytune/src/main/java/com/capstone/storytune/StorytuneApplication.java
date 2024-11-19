package com.capstone.storytune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StorytuneApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorytuneApplication.class, args);
	}

}
