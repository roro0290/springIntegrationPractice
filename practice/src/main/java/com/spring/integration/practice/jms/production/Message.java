package com.spring.integration.practice.jms.production;

import javax.jms.JMSException;

public class Message extends AbstractApplication {
    public static void main(String[] args) throws JMSException {
        // Sending 100 messages is done in the constructor
        Message sender = new Message();
        sender.shutdown();
    }
}
