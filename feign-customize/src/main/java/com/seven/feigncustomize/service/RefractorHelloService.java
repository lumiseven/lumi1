package com.seven.feigncustomize.service;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.seven.demoserviceapi.service.DemoService;

@FeignClient(value="demo-service")
public interface RefractorHelloService extends DemoService{

}
