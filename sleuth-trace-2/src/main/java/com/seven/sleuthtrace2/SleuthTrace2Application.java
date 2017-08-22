package com.seven.sleuthtrace2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SleuthTrace2Application {

	public static void main(String[] args) {
		SpringApplication.run(SleuthTrace2Application.class, args);
	}
}
