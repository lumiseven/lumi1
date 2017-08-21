package com.seven.streamhello.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.seven.streamhello.StreamHelloApplication;
import com.seven.streamhello.sender.SinkSender;

@EnableBinding(value={Sink.class, SinkSender.class})//增加sinksend接口的指定，使spring cloud stream能够创建出对应的实例
public class SinkReceiver {
	
	private static Logger logger = LoggerFactory.getLogger(StreamHelloApplication.class);

	@StreamListener(Sink.INPUT)
	public void receive(Object payload) {
		logger.info("received: " + payload);
	}
	
}
