package com.spring.integration.practice.jms.basics;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class DestinationJMS {

    public ConnectionFactory createConnectionFactory(){
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    public Connection createConnection(ConnectionFactory cf) throws JMSException {
        return cf.createConnection();
    }

    public Session createMsgSession(Connection conn) throws JMSException {
        return conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
    }


    public void sendMsgToDestination(String message, Session session, Destination dst) throws JMSException {
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(dst);
        messageProducer.send(msg);
    }

    // Pass a message listener for asynchronous queue
    public MessageConsumer createMsgConsumer(Session session, Destination dst, MessageListener messageListener) throws JMSException {
        MessageConsumer messageConsumer = session.createConsumer(dst);
        messageConsumer.setMessageListener(messageListener);
        return messageConsumer;
    }

    // Use an overloaded method for topic & Durable Subscriptions

    public static void main(String[] args) throws JMSException {
        DestinationJMS app = new DestinationJMS();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        Session session = app.createMsgSession(conn);

        // Define destination type here and pass in as argument
        Destination dst = session.createQueue("QUEUE_1");
        app.createMsgConsumer(session,dst,msg->{
            System.out.println(msg);
        });
        app.sendMsgToDestination("hello world",session,dst);
        conn.start();
    }

}
