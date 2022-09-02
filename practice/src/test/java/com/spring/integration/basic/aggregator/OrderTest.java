package com.spring.integration.basic.aggregator;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class OrderTest {

    @Autowired
    OrderGateway orderGateway;

    @Autowired
    ApplicationContext context;

    @Test
    public void testOrderAggregator(){
        Order p1 = new Order("SINGLE", 11);
        Order p2 = new Order("RECURRING", 25);
        Order p3 = new Order("SINGLE", 32);
        Order p4 = new Order("RECURRING", 15);

        orderGateway.process(MessageBuilder.withPayload(p1).setHeader("name","foo").build());
        QueueChannel orderChannel = context.getBean("orderOutChannel", QueueChannel.class);

        Message<List> processedOrders = (Message<List>) orderChannel.receive(10000);
        assertNull(processedOrders);

        orderGateway.process(MessageBuilder.withPayload(p2).setHeader("name","moo").build());
        orderGateway.process(MessageBuilder.withPayload(p3).setHeader("name","foo").build());
        orderGateway.process(MessageBuilder.withPayload(p4).setHeader("name","moo").build());
        processedOrders = (Message<List>) orderChannel.receive(10000);
        //assertNotNull(processedOrders);
    }

}
