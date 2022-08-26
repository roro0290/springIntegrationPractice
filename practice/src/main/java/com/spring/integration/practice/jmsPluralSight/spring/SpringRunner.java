package com.spring.integration.practice.jmsPluralSight.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.spring.integration.practice.jms.spring"})
public class SpringRunner {
    public static void main(String... args) {
        SpringApplication.run(SpringRunner.class, args);
    }
}


