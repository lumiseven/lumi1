package com.seven.ribboncustomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.seven.ribboncustomer.service.DemoService;

@RestController
public class CustomerController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/ribbon-consumer", method=RequestMethod.GET)
	public String helloConsumer(){
		return restTemplate.getForEntity("http://demo-service/hello", String.class).getBody();
	}
	
	
	@Autowired
	private DemoService demoService;
	
	@RequestMapping(value="/ribbon-demo-consumer", method=RequestMethod.GET)
	public String demoConsumer(){
		return demoService.demoService();
	}

}
