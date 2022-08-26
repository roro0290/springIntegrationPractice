package com.spring.integration.basic.helloWorld.sampleThree;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;

@Configuration @EnableIntegration @IntegrationComponentScan
public class HelloConfig {
    @Bean
    public IntegrationFlow helloFlow(){
        return IntegrationFlows
                .from("msgChannel")
                .handle(msg -> HelloService.sayHello((Message<String>) msg) )
                .get();
    }
}
