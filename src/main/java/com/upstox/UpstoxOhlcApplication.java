package com.upstox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start the application from main method 
 * @author arastogi
 */
@SpringBootApplication
@EnableAutoConfiguration
public class UpstoxOhlcApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpstoxOhlcApplication.class, args);
	}

}
