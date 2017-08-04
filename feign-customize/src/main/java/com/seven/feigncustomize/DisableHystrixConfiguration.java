//package com.seven.feigncustomize;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
//import feign.Feign;
//
//@Configuration
//public class DisableHystrixConfiguration {
//	
//	@Bean
//	@Scope("prototype")//used in this feign client and not for global
//	public Feign.Builder feignBuilder(){
//		return Feign.builder();
//	}
//
//}
