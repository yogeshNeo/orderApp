package com.orderApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class OrderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderAppApplication.class, args);
		log.info("Order App Running on http://localhost:8080");
	}

}
