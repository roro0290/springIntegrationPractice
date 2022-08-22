package com.spring.integration.practice.jms.production;

import javax.jms.JMSException;

public class MessageSender extends AbstractSenderApplication{
    public static void main(String[] args) throws JMSException {
        // Sending 100 messages is done in the constructor
        MessageSender sender = new MessageSender();
        sender.shutdown();
    }
}
