package com.spring.integration.basic.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.messaging.MessageChannel;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import static com.spring.integration.basic.jms.AdapterSample.outputToUserDst;
import static com.spring.integration.basic.jms.AdapterSample.userInputSource;

@EnableIntegration @Configuration
public class GatewaySample {

    @Autowired
    ToUpperCaseService toUpperCaseService;

    @Bean // OUTBOUND Gateway
    public IntegrationFlow outboundGatewayFlow(ConnectionFactory cf){
        return IntegrationFlows
                .from(userInputSource()) // from stdin through stdin adapter
                .handle(Jms.outboundGateway(cf).requestDestination("requestQueue").replyDestination("replyQueue"))// send to JMS Queue through Outbound Gateway
                .handle(outputToUserDst())
                .get();
    }

    @Bean // INBOUND Gateway
    public IntegrationFlow inboundGatewayFlow(ConnectionFactory cf){
        return IntegrationFlows
                .from(Jms.inboundGateway(cf).requestDestination("requestQueue").defaultReplyQueueName("replyQueue")) // listen on JMS Queue
                //.transform(String.class,String::toUpperCase)
                .handle(toUpperCaseService)
                .get();
    }

}
