package com.library.jafa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JafaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JafaApplication.class, args);
	}

}
