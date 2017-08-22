package com.seven.streamproducer.sender;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

//@EnableBinding(value={SinkSender1.SinkOutput.class})
public class SinkSender1 {
	
	private static Logger logger = LoggerFactory.getLogger(SinkSender1.class);
	
	@Bean
	@InboundChannelAdapter(value=SinkOutput.OUTPUT)
	public MessageSource<Date> timerMessageSource() {
		return () -> new GenericMessage<>(new Date());
	}
	
	public interface SinkOutput {
		String OUTPUT = "input";
		
		@Output(SinkOutput.OUTPUT)
		MessageChannel output();
	}
	
	//can not work
//	@Transformer(inputChannel=Sink.INPUT, outputChannel=SinkOutput.OUTPUT)
//	public Object transform(Date message) {
//		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(message);
//	}
	
}
