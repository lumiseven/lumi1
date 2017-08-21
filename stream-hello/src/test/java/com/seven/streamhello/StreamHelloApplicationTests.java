package com.seven.streamhello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.seven.streamhello.sender.SinkSender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamHelloApplicationTests {

	
	@Autowired
	private SinkSender sinkSender;
	
	@Autowired
	private MessageChannel input;
	
	@Test
	public void contextLoads() {
//		sinkSender.output().send(MessageBuilder.withPayload("from SinkSender").build());
		input.send(MessageBuilder.withPayload("from MessageChannel input").build());
	}

}
