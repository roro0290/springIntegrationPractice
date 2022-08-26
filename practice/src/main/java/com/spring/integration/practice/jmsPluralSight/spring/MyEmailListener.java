package com.spring.integration.practice.jmsPluralSight.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MyEmailListener {

    Log LOGGER = LogFactory.getLog(MyEmailListener.class);

    // create new instance of DefaultMessageListenerContainer
    // When app starts, will deliver email to this method
    // concurrency refers to number of consumers
    @JmsListener(destination="EMAILS_QUEUE",concurrency = "3-10")
    public void onMessage(MyEmail email){
        System.out.println("Received: "+email);
    }

}
