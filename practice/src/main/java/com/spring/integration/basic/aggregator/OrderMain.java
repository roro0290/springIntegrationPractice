package com.spring.integration.basic.aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.spring.integration.basic.aggregator"})
public class OrderMain {
    public static void main(String... args) {
        SpringApplication.run(OrderMain.class, args);
    }
}


