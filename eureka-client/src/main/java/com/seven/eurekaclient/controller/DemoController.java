package com.seven.eurekaclient.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seven.eurekaclient.entity.User;

@RestController
public class DemoController {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String index(){
		ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		logger.info("/hello, host:" + instance.getHost() + ", service_id: " + instance.getServiceId());;
		return "demo eureka client";
	}
	
	@RequestMapping(value = "/hello1", method = RequestMethod.GET)
	public String hello(@RequestParam String name){
		return "hello " + name;
	}
	
	@RequestMapping(value = "/hello2", method = RequestMethod.GET)
	public User hello(@RequestHeader String name, @RequestHeader Integer age){
		return new User(name, age);
	}
	
	@RequestMapping(value = "/hello3", method = RequestMethod.POST)
	public String hello(@RequestBody User user){
		return "hello " + user.getName() + ", " + user.getAge();
	}
}
