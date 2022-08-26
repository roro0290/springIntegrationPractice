package com.spring.integration.practice.jmsPluralSight.production;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.JMSException;

public class Sample1 extends AbstractApplication{
    public static void main(String... args) throws JMSException {
        Sample1 sample = new Sample1();
        sample.start(); // start consuming messages
    }

    private DefaultMessageListenerContainer container;

    private void start() throws JMSException {
        container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setConcurrentConsumers(5); // set up 5 consumers
        container.setCacheLevel(DefaultMessageListenerContainer.CACHE_CONSUMER);
        container.setDestinationName("PROD_SAMPLE_DESTINATION");
        container.setMessageListener(
                new DelayingMessageListener("Default", 10));
        container.setAutoStartup(true);
        container.initialize();
        container.start();
    }

    @Override
    public void shutdown() throws JMSException {
        container.stop();
        super.shutdown();
    }
}
