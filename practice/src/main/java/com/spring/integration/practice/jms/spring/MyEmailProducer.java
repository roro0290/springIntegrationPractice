package com.spring.integration.practice.jms.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MyEmailProducer {

    private static final Log LOGGER = LogFactory.getLog(MyEmailProducer.class);

    // Must inject JmsTemplate into producer
    private JmsTemplate jmsTemplate;

    @Autowired
    public MyEmailProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    // call the JmsTemplate send method
    @Scheduled(fixedDelay = 5000) // create an email every 5 seconds and put it on the queue
    public void generateEmail(){
        MyEmail email = new MyEmail();
        email.setName("roro");
        email.setEmailAddress("roro@gmail.com");
        email.setEmailBody("sending an email to roro");
        // this email will be sent in a json format
        LOGGER.info("email sent");
        jmsTemplate.convertAndSend("EMAILS_QUEUE",email);
    }
}
