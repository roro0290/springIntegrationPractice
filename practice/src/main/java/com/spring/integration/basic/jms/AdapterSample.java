package com.spring.integration.basic.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.integration.stream.CharacterStreamReadingMessageSource;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;

import javax.jms.ConnectionFactory;

@Configuration @EnableIntegration
public class AdapterSample {

    @Bean
    public static CharacterStreamReadingMessageSource userInputSource(){
        // Create a source that reads from System.in
        return CharacterStreamReadingMessageSource.stdin();
    }

    @Bean
    public static CharacterStreamWritingMessageHandler outputToUserDst(){
        return CharacterStreamWritingMessageHandler.stdout();
    }

    //@Bean
    public IntegrationFlow sendMessageFlow(@Qualifier("activeMQConnectionFactory") ConnectionFactory cf){
        return IntegrationFlows
                .from(userInputSource())
                .handle(Jms.outboundAdapter(cf).destination("requestQueue"))
                .get();
    }

    //@Bean
    public IntegrationFlow receiveMessageFlow(@Qualifier("activeMQConnectionFactory")ConnectionFactory cf){
        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(cf).destination("requestQueue"))
                .handle(outputToUserDst())
                .get();
    }

}
