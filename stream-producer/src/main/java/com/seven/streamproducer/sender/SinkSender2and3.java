package com.seven.streamproducer.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.seven.streamproducer.StreamProducerApplication;

//@EnableBinding(value={SinkSender2and3.SinkOutput.class})
public class SinkSender2and3 {

    private static Logger logger = LoggerFactory.getLogger(StreamProducerApplication.class);

    @Bean
    @InboundChannelAdapter(value = Sink.INPUT)
    public MessageSource<String> timerMessageSource() {
        return () -> new GenericMessage<>("{\"name\":\"seven\", \"age\":23}");
    }

    public interface SinkOutput {

        String OUTPUT = "input";

        @Output(SinkOutput.OUTPUT)
        MessageChannel output();

    }
}
