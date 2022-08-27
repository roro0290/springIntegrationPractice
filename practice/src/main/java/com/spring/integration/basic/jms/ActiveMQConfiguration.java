package com.spring.integration.basic.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@EnableJms
@Configuration // This class is used to create the ActiveMQ
public class ActiveMQConfiguration {

    @Bean
    public ConnectionFactory activeMQConnectionFactory(ActiveMQProperties properties){
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(properties.getBrokerUrl());
        cf.getPrefetchPolicy().setQueuePrefetch(1);
        RedeliveryPolicy redeliveryPolicy = cf.getRedeliveryPolicy();
        redeliveryPolicy.setUseExponentialBackOff(true);
        redeliveryPolicy.setMaximumRedeliveries(4);
        return cf;
    }

    @Bean @ConfigurationProperties("spring.activemq")
    public ActiveMQProperties activeMQProperties(){
        return new ActiveMQProperties();
    }

    @Bean
    public Queue requestQueue(){
        return new ActiveMQQueue("REQUEST_QUEUE");
    }

}
