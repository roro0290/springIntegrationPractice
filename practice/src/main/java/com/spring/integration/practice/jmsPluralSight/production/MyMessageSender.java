package com.spring.integration.practice.jmsPluralSight.production;

import javax.jms.JMSException;

public class MyMessageSender extends AbstractApplication {
    public static void main(String[] args) throws JMSException {
        // Sending 100 messages is done in the constructor
        MyMessageSender sender = new MyMessageSender();
        sender.shutdown();
    }
}
