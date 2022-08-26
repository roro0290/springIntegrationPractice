package com.spring.integration.practice.helloWorld.poller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;

@Configuration @EnableIntegration
public class PollerConfig {

    @Bean
    public IntegrationFlow pollerFlow(){
        return IntegrationFlows // requires a Message<Object> poller is to check the system time
                .from(()-> new GenericMessage<>(System.currentTimeMillis()),
                        e -> e.poller(p -> p.fixedRate(5000))) // place inbound in from
                .log(LoggingHandler.Level.INFO,
                        "TEST_LOGGER",
                        m -> "message payload: " + m.getPayload()) // place outbound in handle
                .get();
    }

}
