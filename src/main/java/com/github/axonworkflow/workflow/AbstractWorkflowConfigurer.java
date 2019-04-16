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

/**
 * Abstract configuration for workflow infrastructure based on Spring State Machine. Intended to be subclassed by the concrete
 * configuration of the Saga's workflow. Configures {@link StateMachineRuntimePersister} for persisting the instances of
 * the workflow, and an instance of {@link StateMachineService} to be used by {@link WorkflowEngine} for processing
 * workflow events.
 *
 * @param <S> enumeration of Saga's states
 * @param <E> enumeration of Saga's events
 */
@EnableStateMachineFactory
public abstract class AbstractWorkflowConfigurer<S extends Enum<S>, E extends Enum<E>> extends StateMachineConfigurerAdapter<S, E> {

    @Autowired
    private JpaStateMachineRepository jpaStateMachineRepository;

    @Bean
    public StateMachineRuntimePersister<S, E, String> jpaStateMachineRuntimePersister() {
        return new JpaPersistingStateMachineInterceptor<>(jpaStateMachineRepository);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<S, E> config) throws Exception {
        config.withPersistence().runtimePersister(jpaStateMachineRuntimePersister());
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

    protected <A extends WorkflowEvent<E>> A getAxonEvent(StateContext<S, E> context, Class<A> type) {
        A axonEvent = context.getMessageHeaders().get(WorkflowConstants.AXON_EVENT, type);
        Assert.notNull(axonEvent, "No Axon event in message header");
        return axonEvent;
    }

}
