package com.seven.demoserviceapi.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.seven.demoserviceapi.dto.User;

@RequestMapping("/refractor")
public interface DemoService {

	/*
	 * note：
	 * 在feign中，参数绑定必须通过value属性来指定具体的参数名
	 * 否则会出现 IllegalStateException
	 */
	@RequestMapping(value="/hello4", method=RequestMethod.GET)
	String hello(@RequestParam("name") String name);
	
	@RequestMapping(value="/hello5", method=RequestMethod.GET)
	User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);
	
	@RequestMapping(value="/hello6", method=RequestMethod.POST)
	String hello(@RequestBody User user);
	
	/*
	 * 测试ribbon重试配置
	 */
	@RequestMapping(value="/hello7", method=RequestMethod.GET)
	String hello() throws Exception;
	
}
