package com.seven.streamconsumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

//@EnableBinding(value={Processor.class})
public class App1 {
	
	private static Logger logger = LoggerFactory.getLogger(App1.class);
	
	@StreamListener(Processor.INPUT)
	@SendTo(Processor.OUTPUT)
	public Object receive(Object payload) {
		logger.info("Received: " + payload);
		return "Input channel had received message -- " + payload;
	}

}
