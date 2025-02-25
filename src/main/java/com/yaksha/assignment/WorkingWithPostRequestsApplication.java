package com.yaksha.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.yaksha.assignment")
public class WorkingWithPostRequestsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkingWithPostRequestsApplication.class, args);
	}
}
