package com.seven.feigncustomize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignCustomizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignCustomizeApplication.class, args);
	}
}
