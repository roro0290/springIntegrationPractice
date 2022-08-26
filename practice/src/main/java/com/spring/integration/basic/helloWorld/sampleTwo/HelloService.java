package com.spring.integration.basic.helloWorld.sampleTwo;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service @MessageEndpoint
public class HelloService {

    @ServiceActivator(inputChannel = "inputMsgChannel", outputChannel = "outputMsgChannel")
    public Message<String> sayHello(Message<String> name){
        return MessageBuilder.withPayload("Hello "+ name.getPayload() + "!").build();
    }

}
