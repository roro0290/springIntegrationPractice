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
        messageProducer.setPriority(9); // 0 - 4 are normal, 5 - 9 are expedited
        messageProducer.setTimeToLive(10000); // default = 0 ⇒ will never expire
        messageProducer.send(msg,
                DeliveryMode.NON_PERSISTENT,
                9, // priority
                10000); // time to live
    }

    // Pass a message listener for asynchronous queue
    public MessageConsumer createMsgConsumer(Session session, Destination dst, MessageListener messageListener) throws JMSException {
        MessageConsumer messageConsumer = session.createConsumer(dst);
        messageConsumer.setMessageListener(messageListener);
        return messageConsumer;
    }

    // Use an overloaded method for topic & Durable Subscriptions
    public TopicSubscriber createTopicSubscriber(Session session, Destination dst, MessageListener messageListener, String subscriptionName) throws JMSException {
        // provide name of subscription
        TopicSubscriber topicSubscriber = session.createDurableSubscriber((Topic) dst,subscriptionName);
        topicSubscriber.setMessageListener(messageListener);
        return topicSubscriber;
    }

    public static void main(String[] args) throws JMSException {
        DestinationJMS app = new DestinationJMS();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        conn.setClientID("MyUniqueClientID"); // must define client ID on the connection
        Session session = app.createMsgSession(conn);

        // Define destination type here and pass in as argument
        /*
        Destination dst = session.createQueue("QUEUE_1");
        app.createMsgConsumer(session,dst,msg->{
            System.out.println(msg);
        });
        app.sendMsgToDestination("hello world",session,dst);
        */
        Destination dst2 = session.createTopic("TOPIC_1");
        TopicSubscriber subscriber = app.createTopicSubscriber(session,dst2,msg->{
            System.out.println(msg);
        },"test-subscription");
        app.sendMsgToDestination("hello TOPIC",session,dst2);

        conn.start();

        // Free up resources
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                try{
                    super.run();
                    conn.stop();
                    subscriber.close(); // when we close topicSubscriber = keep any messages for me until I connect again
                    session.close();
                    conn.close();

                    // If you no longer want to keep messages on the broker after disconnecting, need to close session
                    // by unsubscribing using the same subscription name as earlier
                    session.unsubscribe("test-subscription");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
