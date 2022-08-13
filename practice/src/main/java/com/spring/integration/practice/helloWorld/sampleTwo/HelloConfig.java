package com.example.springIntegration.helloWorld.sampleTwo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
public class HelloConfig {

    @Bean // Use builder class to create the channel
    public MessageChannel inputMsgChannel(){
        return MessageChannels.direct().get();
    }

    @Bean // Use builder class to create the channel
    public MessageChannel outputMsgChannel(){
        return MessageChannels.queue().get();
    }

}
