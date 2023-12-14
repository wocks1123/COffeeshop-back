package com.wocks.coffeeshopback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class COffeeShopBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(COffeeShopBackApplication.class, args);
	}

}
