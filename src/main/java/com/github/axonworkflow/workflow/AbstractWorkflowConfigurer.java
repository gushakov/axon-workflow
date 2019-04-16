package com.github.axonworkflow.workflow;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.util.Assert;

// based on the example from https://github.com/spring-projects/spring-statemachine/blob/master/spring-statemachine-samples/datapersist/src/main/java/demo/datapersist/StateMachineConfig.java

@EnableStateMachineFactory
public abstract class AbstractWorkflowConfigurer<S extends Enum<S>, E extends Enum<E>> extends StateMachineConfigurerAdapter<S, E> {

    @Autowired
    private StateMachineRuntimePersister<S, E, String> stateMachineRuntimePersister;

    @Bean
    public StateMachineRuntimePersister<S, E, String> jpaStateMachineRuntimePersister(JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<S, E> config) throws Exception {
        config.withPersistence().runtimePersister(stateMachineRuntimePersister);
    }

    @Bean
    public StateMachineService<S, E> stateMachineService(StateMachineFactory<S, E> stateMachineFactory,
                                                         StateMachineRuntimePersister<S, E, String> jpaStateMachineRuntimePersister) {
        return new DefaultStateMachineService<>(stateMachineFactory, jpaStateMachineRuntimePersister);
    }

    @Bean
    public WorkflowEngine<S, E> workflowEngine(StateMachineService<S, E> stateMachineService) {
        return new WorkflowEngine<>(stateMachineService);
    }

    protected <A extends WorkflowEvent<E>> A getAxonEvent(StateContext<S, E> context, Class<A> type){
        A axonEvent = context.getMessageHeaders().get(WorkflowConstants.AXON_EVENT, type);
        Assert.notNull(axonEvent, "No Axon event in message header");
        return axonEvent;
    }

}
