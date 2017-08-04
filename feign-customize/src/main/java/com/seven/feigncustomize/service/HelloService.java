package com.seven.feigncustomize.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//import com.seven.feigncustomize.DisableHystrixConfiguration;
import com.seven.feigncustomize.entity.User;
import com.seven.feigncustomize.fallback.HelloServiceFallback;

@FeignClient(name="demo-service", fallback=HelloServiceFallback.class)
//@FeignClient(name="demo-service", configuration=DisableHystrixConfiguration.class)//configure not use hystrix
//@FeignClient(name="demo-service", configuration=FeignLogConfiguration.class)//log level special setting
public interface HelloService {
	
	@RequestMapping("/hello")
	String hello();

	/*
	 * note：
	 * 在feign中，参数绑定必须通过value属性来指定具体的参数名
	 * 否则会出现 IllegalStateException
	 */
	@RequestMapping(value="/hello1", method=RequestMethod.GET)
	String hello(@RequestParam("name") String name);
	
	@RequestMapping(value="/hello2", method=RequestMethod.GET)
	User hello(@RequestHeader("name") String name, @RequestHeader("age") Integer age);
	
	@RequestMapping(value="/hello3", method=RequestMethod.POST)
	String hello(@RequestBody User user);
}
