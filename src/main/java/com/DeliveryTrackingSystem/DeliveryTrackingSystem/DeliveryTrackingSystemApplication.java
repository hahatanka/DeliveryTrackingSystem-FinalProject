package com.DeliveryTrackingSystem.DeliveryTrackingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class DeliveryTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryTrackingSystemApplication.class, args);
	}

	@GetMapping("/error")
	public String error(){
		return "error";
	}



}
