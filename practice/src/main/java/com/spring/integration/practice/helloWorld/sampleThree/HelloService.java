package com.example.springIntegration.helloWorld.sampleThree;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service @MessageEndpoint
public class HelloService {

    @ServiceActivator(inputChannel = "msgChannel")
    public static Message<String> sayHello(Message<String> name){
        return MessageBuilder.withPayload("Hello "+ name.getPayload() + "!").build();
    }

}
