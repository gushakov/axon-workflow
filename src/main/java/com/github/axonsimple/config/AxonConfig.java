package com.github.axonsimple.config;


import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class AxonConfig {

    private static final Logger logger = getLogger(AxonConfig.class);

    // Simple command bus using Spring's PlatformTransactionManager

    @Bean
    public CommandBus commandBus(PlatformTransactionManager platformTransactionManager) {
        return SimpleCommandBus.builder()
                .transactionManager(new SpringTransactionManager(platformTransactionManager))
                .build();
    }

    // Simple event bus

    @Bean
    public EventBus eventBus() {
        return SimpleEventBus.builder().build();
    }

}
