package com.spring.integration.practice.helloWorld.sampleThree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.MessageBuilder;


@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.example.springIntegration.helloWorld.sampleThree"})
public class HelloApp extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(HelloApp.class);

    @Autowired
    HelloGateway helloGateway;

    public static void main(String[] args) {
        SpringApplication.run(HelloApp.class, args);
    }

    @Override
    public void run(String...args) throws Exception{
        Message<String> requestMsg = MessageBuilder.withPayload("world").build();
        Message<String> replyMsg = helloGateway.sayHello(requestMsg);
        logger.info(replyMsg.getPayload());
    }

}
