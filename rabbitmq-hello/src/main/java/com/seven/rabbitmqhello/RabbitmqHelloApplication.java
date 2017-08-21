package com.seven.rabbitmqhello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RabbitmqHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqHelloApplication.class, args);
	}
}
