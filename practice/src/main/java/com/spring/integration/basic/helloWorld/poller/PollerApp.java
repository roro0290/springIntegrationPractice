package com.spring.integration.basic.helloWorld.poller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;


@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.example.springIntegration.basic.helloWorld.poller"})
public class PollerApp extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PollerApp.class, args);
    }

    @Override
    public void run(String...args) throws Exception{
        Message<String> requestMsg = MessageBuilder.withPayload("world").build();

    }

}
