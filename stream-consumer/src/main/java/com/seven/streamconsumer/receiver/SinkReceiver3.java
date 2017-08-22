package com.seven.streamconsumer.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.seven.streamconsumer.StreamConsumerApplication;
import com.seven.streamconsumer.entity.User;

//@EnableBinding(value = {Sink.class})
public class SinkReceiver3 {

	private static Logger logger = LoggerFactory.getLogger(StreamConsumerApplication.class);

	@StreamListener(Sink.INPUT)
	public void receive(User user) {
		logger.info("Received: " + user);
	}

}