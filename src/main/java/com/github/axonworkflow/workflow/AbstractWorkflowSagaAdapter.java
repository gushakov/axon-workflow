package com.github.axonworkflow.workflow;


import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class AbstractWorkflowSagaAdapter<S extends Enum<S>, E extends Enum<E>, A extends WorkflowEvent<E>> implements WorkflowListenerCallback<S, E> {

    private static final Logger logger = getLogger(AbstractWorkflowSagaAdapter.class);

    @Autowired
    private transient WorkflowEngine<S, E> workflowEngine;

    @StartSaga
    @SagaEventHandler(associationProperty = "flowId")
    public void startSaga(A startEvent) {
        logger.debug("[Workflow Saga Adapter] Starting saga, event: {}, flow ID: {}", startEvent, startEvent.getFlowId());
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
