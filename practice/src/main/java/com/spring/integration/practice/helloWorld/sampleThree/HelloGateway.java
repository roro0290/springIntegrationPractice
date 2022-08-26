package com.spring.integration.practice.helloWorld.sampleThree;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

// gateway will listen on this channel and call the method when a message reach
@MessagingGateway(name="helloGateway", defaultRequestChannel = "msgChannel")
public interface HelloGateway {

    @Gateway
    Message<String> sayHello(Message<String> msg);

}
