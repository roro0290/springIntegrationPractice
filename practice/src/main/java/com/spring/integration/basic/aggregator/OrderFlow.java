package com.spring.integration.basic.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@Configuration @EnableIntegration
public class OrderFlow {

    @Autowired
    OrderService orderService;

    @Bean
    public MessageChannel orderOutChannel(){
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow myOrderFlow(){
        return IntegrationFlows
                .from("orderInChannel")
                .aggregate(a-> // correlate messages that have the same payload type
                        a.correlationStrategy(m -> m.getHeaders().get("name")) // how to filter a field from message payload
                                .releaseStrategy(g -> g.size()==2)
                ).handle(orderService)
                .channel("orderOutChannel") // test case needs to check from this
                .get();
    }

}
