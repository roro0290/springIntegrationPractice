package com.spring.integration.practice.jmsPluralSight.basics;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;

import javax.jms.*;

public class BasicJMS {

    public ConnectionFactory createConnectionFactory(){
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616" //broker url on which Active MQ is listening on
        );
    }

    // Queue specific connection factory
    public QueueConnectionFactory createQueueConnectionFactory(){
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616" //broker url on which Active MQ is listening on
        );
    }

    // Topic specific connection factory
    public TopicConnectionFactory createTopicConnectionFactory(){
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616" //broker url on which Active MQ is listening on
        );
    }

    // Using MS in a distributed transactional environment
    public XAConnectionFactory createXAConnectionFactory(){
        return new ActiveMQXAConnectionFactory(
                "tcp://localhost:61616" //broker url on which Active MQ is listening on
        );
    }

    public Connection createConnection(ConnectionFactory cf) throws JMSException {
        return cf.createConnection();
    }

    // Topic specific connection
    public TopicConnection createTopicConnection(TopicConnectionFactory cf) throws JMSException {
        return cf.createTopicConnection();
    }

    // Queue specific connection
    public QueueConnection createQueueConnection(QueueConnectionFactory cf) throws JMSException {
        return cf.createQueueConnection();
    }

    public Session createSessionFromConn(Connection connection) throws JMSException {
        return connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
    }

    // Topic specific session
    public TopicSession createTopicSession(TopicConnection connection) throws JMSException {
        return connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
    }

    // Queue specific session
    public QueueSession createQueueSession(QueueConnection connection) throws JMSException {
        return connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
    }

    // General Implementation to send message to a QUEUE
    public void sendTextMessageToQueue(String message, Session session) throws JMSException {
        Queue queue = session.createQueue("TEST_QUEUE");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(msg);
    }

    // Queue Specific Implementation
    public void sendTextMessageToQueue(String message, QueueSession queueSession) throws JMSException {
        Queue queue = queueSession.createQueue("TEST_QUEUE");
        TextMessage msg = queueSession.createTextMessage(message);
        QueueSender messageProducer = queueSession.createSender (queue);
        messageProducer.send(msg);
    }

    // General Implementation to send message to a TOPIC
    public void sendTextMessageToTopic(String message, Session session) throws JMSException {
        Topic topic = session.createTopic("TEST_TOPIC");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.send(msg);
    }

    // Topic Specific Implementation to send message to a TOPIC
    public void sendTextMessageToTopic(String message, TopicSession topicSession) throws JMSException {
        Topic topic = topicSession.createTopic("TEST_TOPIC");
        TextMessage msg = topicSession.createTextMessage(message);
        TopicPublisher messageProducer = topicSession.createPublisher(topic);
        messageProducer.send(msg);
    }
    
    
    public MessageConsumer createConsumerFromQueue(Session session,
                                       String destination,
                                       MessageListener messageListener) throws JMSException {
        Queue queue = session.createQueue(destination);
        MessageConsumer consumer = session.createConsumer(queue); // consume message from this queue

        // To avoid this, can do polling
        /*boolean someCondition = true;
        while(someCondition){
            Message msg = consumer.receive(500); // // it will block indefinitely until a message is received. pass in timeout value
            if(msg != null){
                // do something with the message
            }
        }
         */

        // Message Listener
        consumer.setMessageListener(messageListener);
        return consumer;
    }


    public static void main(String[] args) throws JMSException {
        BasicJMS app = new BasicJMS();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection connection = app.createConnection(cf);
        Session session = app.createSessionFromConn(connection);
        MessageConsumer consumer = app.createConsumerFromQueue(
                session,
                "TEST_QUEUE",
                (msg -> {
                    // do sth with the message
                    System.out.println(msg);
                })
        );
        app.sendTextMessageToQueue("hello world",session);

        //  Instruct Connection object to start delivery of messages by calling the start() method.
        connection.start();

        // Free up resources
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                try{
                    super.run();
                    connection.stop();
                    consumer.close();
                    session.close();
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
