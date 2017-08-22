package com.seven.streamconsumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.rxjava.EnableRxJavaProcessor;
import org.springframework.cloud.stream.annotation.rxjava.RxJavaProcessor;
import org.springframework.context.annotation.Bean;

//@EnableRxJavaProcessor
public class App1_2 {
	
	private static Logger logger = LoggerFactory.getLogger(App1_2.class);
	
	@Bean
	public RxJavaProcessor<String, String> processor(){
		return iS -> iS.map(data -> {
			logger.info("Received: " + data);
			return data;
		}).buffer(5).map(data -> String.valueOf("From Input Channel Return - " + data));
	}

}
