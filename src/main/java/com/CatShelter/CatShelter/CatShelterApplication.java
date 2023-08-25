package com.CatShelter.CatShelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CatShelterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatShelterApplication.class, args);
	}

}
