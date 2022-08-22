package com.spring.integration.practice.jms.production;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public abstract class AbstractApplication {

    public AbstractApplication() {
        try {
            connect(); // set up connection
            sendMessages(); // send the messages
        } catch (JMSException e) {
            e.printStackTrace();
            System.exit(1);
        }
        addShutdownHook();
    }

    protected ConnectionFactory connectionFactory;
    protected Connection connection;
    protected Session session;
    protected MessageProducer messageProducer;
    private Queue queue;

    private void connect() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue("PROD_SAMPLE_DESTINATION");
        messageProducer = session.createProducer(queue);
    }

    protected void sendMessages() throws JMSException {
        for(int x=0;x<100;x++){
            messageProducer.send(session.createTextMessage("Message "+x));
        }
    }

    void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    shutdown();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void shutdown() throws JMSException {
        if (null != messageProducer) {
            messageProducer.close();
        }
        connection.close();
        session.close();
    }
}
