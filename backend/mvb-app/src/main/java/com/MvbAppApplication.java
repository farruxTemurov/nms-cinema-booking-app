package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MvbAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvbAppApplication.class, args);
		System.err.println("Movie booking app's backend is up on port 9090!");
	}

}
