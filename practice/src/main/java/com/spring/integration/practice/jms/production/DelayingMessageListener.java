package com.spring.integration.practice.jms.production;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

// some delay is given to mimic real life processing
public class DelayingMessageListener implements MessageListener {

    private long delay = 100L;
    private String id = "Listener";

    public DelayingMessageListener(String id, long delay) {
        this.delay = delay;
        this.id = id;
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage)message;
            System.out.println(id + " received : " + textMessage.getText());
            Thread.sleep(delay);
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
