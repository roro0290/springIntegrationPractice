package com.spring.integration.practice.jms.production;

import javax.jms.*;

// MESSAGE CONSUMER
public class Sample2 extends AbstractApplication{

    public static void main(String... args) throws JMSException {
        Sample2 sample2 = new Sample2();
        sample2.start(); // start consuming messages
    }

    private MessageConsumer messageConsumer;
    private javax.jms.MessageProducer messageProducer;
    private CorrelationIdReplyMessageListener messageListener;
    private Session sendSession;
    private Session receiveSession;

    private void start() throws JMSException {

        // Consumer is listening to the queue
        startConsumerService();

        // SEND THE MESSAGE
        sendSession = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        messageProducer = sendSession.createProducer(sendSession.createQueue("REQUEST_QUEUE"));
        TextMessage requestMessage = sendSession.createTextMessage("request message");
        System.out.println("Sending request : " + requestMessage.getText());
        messageProducer.send(requestMessage);

        // MessageProducer needs to block and WAIT for response
        TextMessage responseMessage = blockForMessage(requestMessage);
        System.out.println("Reply was : " + responseMessage.getText());
        shutdown();
    }

    private void startConsumerService() throws JMSException {
        receiveSession = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // consume from request queue
        messageConsumer = receiveSession.createConsumer(receiveSession.createQueue("REQUEST_QUEUE"));
        // CorrelationIdReplyMessageListener once it gets the message
        // 1. create the response message
        // 2. set a JMSCorrelationID on the reply
        // 3. sends it to REPLY_QUEUE
        messageListener = new CorrelationIdReplyMessageListener(connection);
        messageConsumer.setMessageListener(messageListener);
        connection.start();
    }

    // Block the current thread of excecution until we receive a response
    private TextMessage blockForMessage(Message message) throws JMSException {
        Queue replyQueue = session.createQueue("REPLY_QUEUE");
        // register a new consumer on the reply queue
        // use a message selector => the MSCorrelationID of the response should equal to the JMSMessageID of what we sent
        // (because this is what we had set in the message listener)
        MessageConsumer consumer = session.createConsumer(replyQueue,
                "JMSCorrelationID = '"
                        + message.getJMSMessageID() + "'");
        Message response = consumer.receive(3000); // set timeout
        consumer.close();
        return (TextMessage) response;
    }

    @Override
    public void shutdown() throws JMSException {
        messageListener.shutdown();
        messageConsumer.close();
        receiveSession.close();
        sendSession.close();
        super.shutdown();
    }
}
