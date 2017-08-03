package com.seven.feigncustomize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.seven.feigncustomize.entity.User;
import com.seven.feigncustomize.service.HelloService;

@RestController
public class CustomizeController {

	@Autowired
	HelloService helloService;
	
	@RequestMapping(value="/feign-customize", method=RequestMethod.GET)
	public String helloCustomize(){
		return helloService.hello();
	}
	
	@RequestMapping(value="/feign-customize2", method=RequestMethod.GET)
	public String helloCustomize2(){
		StringBuilder sb = new StringBuilder();
		sb.append(helloService.hello()).append("\n");
		sb.append(helloService.hello("lumi")).append("\n");
		sb.append(helloService.hello("lumiseven", 22)).append("\n");
		sb.append(helloService.hello(new User("lumiseven", 22))).append("\n");
		return sb.toString();
	}
}
