package com.github.axonworkflow;

import org.axonframework.springboot.autoconfig.JdbcAutoConfiguration;
import org.axonframework.springboot.autoconfig.JpaEventStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

// We do not use auto-configuration for persistent event store, since we
// do not use event sourcing. Note that we need to specifically tell Spring Boot
// where to look for the Axon's JPA entities.

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class,
        JpaEventStoreAutoConfiguration.class, JdbcAutoConfiguration.class})
@EntityScan(basePackages = {"org.axonframework.modelling.saga.repository.jpa",
        "com.github.axonworkflow.query", "com.github.axonworkflow.command"})
public class AxonStateMachineWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxonStateMachineWorkflowApplication.class, args);
    }

}
