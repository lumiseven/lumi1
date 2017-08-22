package com.seven.streamconsumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seven.streamconsumer.StreamConsumerApplication;
import com.seven.streamconsumer.entity.User;

//@EnableBinding(value={Sink.class})
public class SinkReceiver2 {
	
	private static Logger logger = LoggerFactory.getLogger(StreamConsumerApplication.class);
	
	@ServiceActivator(inputChannel=Sink.INPUT)
	public void receive(User user) {
		logger.info("Received: " + user);
	}
	
	@Transformer(inputChannel=Sink.INPUT, outputChannel=Sink.INPUT)
	public User transform(String message) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = objectMapper.readValue(message, User.class);
		return user;
	}

}
