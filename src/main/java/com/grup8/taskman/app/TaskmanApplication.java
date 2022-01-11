package com.grup8.taskman.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // Habilitem els mètodes asíncrons
public class TaskmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskmanApplication.class, args);
	}

}
