package com.tpo.bankjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankjobApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankjobApplication.class, args);
	}

}
