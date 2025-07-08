package com.stratumtech.realtyproperty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RealtyManagementSystemPropertyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealtyManagementSystemPropertyServiceApplication.class, args);
	}

}
