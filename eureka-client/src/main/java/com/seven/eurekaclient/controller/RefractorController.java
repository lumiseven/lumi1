package com.seven.eurekaclient.controller;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seven.demoserviceapi.dto.User;
import com.seven.demoserviceapi.service.DemoService;

@RestController
public class RefractorController implements DemoService{

	@Override
	public String hello(@RequestParam String name){
		return "hello " + name;
	}
	
	@Override
	public User hello(@RequestHeader String name, @RequestHeader Integer age){
		return new User(name, age);
	}
	
	@Override
	public String hello(@RequestBody User user){
		return "hello " + user.getName() + ", " + user.getAge();
	}

	
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Override
	public String hello() throws Exception{
		ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		logger.info("/hello, host:" + instance.getHost() + ", service_id: " + instance.getServiceId());
		
		//suffix time out value
		int sleepTime = new Random().nextInt(3000);
		logger.info("sleepTime: " + sleepTime);
		Thread.sleep(sleepTime);
		return "demo eureka client";
	}
	
}
