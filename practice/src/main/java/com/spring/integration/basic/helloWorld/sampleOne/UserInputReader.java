package com.spring.integration.basic.helloWorld.sampleOne;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.stream.CharacterStreamReadingMessageSource;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class UserInputReader {

    // Input adapter that reads from user STDIN
    @Bean
    public CharacterStreamReadingMessageSource userInputSource(){
        // Create a source that reads from System.in.
        return CharacterStreamReadingMessageSource.stdin();
    }

    // Service Activator, message handler
    // write message to output
    @Bean
    public CharacterStreamWritingMessageHandler targetOutputDest(){
        return CharacterStreamWritingMessageHandler.stdout();
    }

    // Once we have created all our components
    // we need to register our IntegrationFlow as a bean to activate it
    @Bean
    public IntegrationFlow transformInput(){
        return IntegrationFlows.from(userInputSource())
                .handle(targetOutputDest())
                .get();
    }
    
}
