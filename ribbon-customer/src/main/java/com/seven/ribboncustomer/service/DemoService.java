package com.seven.ribboncustomer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DemoService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="demoFallback")
	public String demoService(){
		return restTemplate.getForEntity("http://demo-service/hello", String.class).getBody();
	}
	
	public String demoFallback(){
		return "error";
	}

}
