package com.spring.integration.basic.aggregator;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @ServiceActivator // receives a message with list after aggregation
    public void processOrder(Message<List> orderMessage){

        System.out.println("Orders");
        List<Order> payments = orderMessage.getPayload();
        for(Order pay: payments){
            System.out.println(pay.toString());
        }
    }
}
