package com.spring.integration.practice.jmsPluralSight.spring;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.ConnectionFactory;


@EnableJms @EnableScheduling @Configuration @EnableIntegration
public class SpringBootExample {

    @Bean
    @Primary // Indicates that every time we want a ConnectionFactory, use this
    public ConnectionFactory ActiveMQConnectionFactory(ActiveMQProperties properties){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(properties.getBrokerUrl());
        connectionFactory.getPrefetchPolicy().setQueuePrefetch(1);
        RedeliveryPolicy redeliveryPolicy = connectionFactory.getRedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setMaximumRedeliveries(4); // try 5 times
        return connectionFactory;
    }

    @Bean // Use the activeMQConnectionFactory to create this
    public ConnectionFactory cachingConnectionFactory(
            @Qualifier("ActiveMQConnectionFactory")
                    ConnectionFactory activeMqConnectionFactory) {
        CachingConnectionFactory cachingConnectionFactory
                = new CachingConnectionFactory(activeMqConnectionFactory);
        cachingConnectionFactory.setCacheConsumers(true);
        cachingConnectionFactory.setCacheProducers(true);
        // reconnect when an exception occurs
        cachingConnectionFactory.setReconnectOnException(true);
        return cachingConnectionFactory;
    }

    // Declare Message Converter
    @Bean
    public MessageConverter jacksonMessageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_messageType");
        return converter;
    }

    // Declare implementation for JMSTemplate
    // this is the definition for queue destination
    @Bean @Primary
    public JmsTemplate queueJmsTemplate(
            @Qualifier("cachingConnectionFactory") ConnectionFactory cf
    ){
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
        // allow template to look up destination dynamically based on name
        jmsTemplate.setDestinationResolver(new DynamicDestinationResolver());
        jmsTemplate.setMessageConverter(jacksonMessageConverter());
        return jmsTemplate;
    }

    @Bean
    public JmsTemplate topicJmsTemplate(
            @Qualifier("cachingConnectionFactory") ConnectionFactory cf
    ){
        JmsTemplate jmsTemplate = new JmsTemplate(cf);
        // set to true for topic
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setDestinationResolver(new DynamicDestinationResolver());
        jmsTemplate.setMessageConverter(jacksonMessageConverter());
        return jmsTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.activemq")
    public ActiveMQProperties activeMQProperties() {
        return new ActiveMQProperties();
    }

}
