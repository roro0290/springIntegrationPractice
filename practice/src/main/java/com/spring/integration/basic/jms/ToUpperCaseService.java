package com.spring.integration.basic.jms;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@MessageEndpoint @Service
public class ToUpperCaseService {

    @ServiceActivator
    public String toUpperCaseString(String input) {
        return "JMS response: " + input.toUpperCase();
    }

}
