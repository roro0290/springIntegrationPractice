package com.spring.integration.practice.jms.production;

import javax.jms.*;
import javax.jms.Message;

// Create our own implementation of the Message Listener
public class CorrelationIdReplyMessageListener implements MessageListener {

    private Session session = null;
    private MessageProducer producer = null;
    private Queue replyQueue = null;

    // instantiate this listener with the connection details
    // use connection details to create objects
    public CorrelationIdReplyMessageListener(Connection connection) throws JMSException {
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        replyQueue = session.createQueue("REPLY_QUEUE");
        producer = session.createProducer(replyQueue);
    }

    @Override // Listener will listen and autowire this message
    public void onMessage(Message message) {
        try {
            TextMessage requestMessage = (TextMessage)message;
            TextMessage reply = session.createTextMessage("Response to: " + requestMessage.getText());
            // set it to the same value as the inbound request
            reply.setJMSCorrelationID(message.getJMSMessageID());
            producer.send(replyQueue,reply);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() throws JMSException {
        session.close();
        producer.close();
    }
}
