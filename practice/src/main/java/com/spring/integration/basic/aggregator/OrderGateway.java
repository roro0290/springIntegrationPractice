package com.spring.integration.basic.aggregator;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

// from test case, must send message to this Gateway
@MessagingGateway(defaultRequestChannel = "orderInChannel")
public interface OrderGateway {

    @Gateway
    void process(Message orderMessage);

}
