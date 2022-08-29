package com.spring.integration.basic.jms;

import com.spring.integration.basic.helloWorld.sampleThree.HelloGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;


@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.spring.integration.basic.jms"})
public class JMSApp {

    public static void main(String[] args) {
        SpringApplication.run(JMSApp.class, args);
    }

}
