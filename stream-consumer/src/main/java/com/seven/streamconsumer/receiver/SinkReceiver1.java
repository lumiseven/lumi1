package com.seven.streamconsumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

//@EnableBinding(value={Sink.class})
public class SinkReceiver1 {
	
	private static Logger logger = LoggerFactory.getLogger(SinkReceiver1.class);
	
	@ServiceActivator(inputChannel=Sink.INPUT)
	public void receive(Object payload) {
		logger.info("Received: " + payload);
	}
	
}
