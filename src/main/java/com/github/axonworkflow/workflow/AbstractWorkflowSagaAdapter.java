package com.github.axonworkflow.workflow;


import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Generic adapter which delegates all Saga's events to an instance of {@link WorkflowEngine} to be handled
 * by an instance of a State Machine.
 * @param <S> enumeration of Saga's states
 * @param <E> enumeration of Saga's events
 * @param <A> concrete event which triggers the start of the Saga
 *
 * @see WorkflowEngine
 * @see WorkflowListenerCallback
 */
public abstract class AbstractWorkflowSagaAdapter<S extends Enum<S>, E extends Enum<E>, A extends WorkflowEvent<E>> implements WorkflowListenerCallback<S, E> {

    private static final Logger logger = getLogger(AbstractWorkflowSagaAdapter.class);

    // This engine will handle all of the Saga's events
    @Autowired
    private transient WorkflowEngine<S, E> workflowEngine;

    // Since we only have an annotation to start the Saga, we listen a particular event
    @StartSaga
    @SagaEventHandler(associationProperty = "flowId")
    public void startSaga(A startEvent) {
        logger.debug("[Workflow Saga Adapter] Starting saga, event: {}, flow ID: {}", startEvent, startEvent.getFlowId());

        // Make sure to end saga if the state machine did not accept the event
        if (!processWorkflowEvent(startEvent)) {
            logger.debug("[Workflow Saga Adapter] Saga starting event not accepted by the workflow engine, ending saga, event: {}, flow ID: {}", startEvent,
                    startEvent.getFlowId());
            SagaLifecycle.end();
        }
    }

    @SagaEventHandler(associationProperty = "flowId")
    public void on(WorkflowEvent<E> workflowEvent) {
        processWorkflowEvent(workflowEvent);
    }

    @Override
    public void workflowCompleted() {
        logger.debug("[Workflow Saga Adapter] Completed workflow");
        SagaLifecycle.end();
    }

    private boolean processWorkflowEvent(WorkflowEvent<E> workflowEvent) {
        logger.debug("[Workflow Saga Adapter] Handle event: {}, flow ID: {}", workflowEvent, workflowEvent.getFlowId());
        boolean accepted = workflowEngine.handleWorkflowEvent(workflowEvent, this);
        logger.debug("[Workflow Saga Adapter] Workflow event accepted: {}", accepted);
        return accepted;
    }

}
