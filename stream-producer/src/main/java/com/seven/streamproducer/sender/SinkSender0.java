package com.seven.streamproducer.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import com.seven.streamproducer.StreamProducerApplication;

@EnableBinding(value={Source.class})
public class SinkSender0 {

  private static Logger logger = LoggerFactory.getLogger(StreamProducerApplication.class);

  @Bean
  @InboundChannelAdapter(value = Source.OUTPUT, poller=@Poller(fixedDelay="2000"))
  public MessageSource<String> timerMessageSource() {
      return () -> new GenericMessage<>("{\"name\":\"seven\", \"age\":23}");
  }

}