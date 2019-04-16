package com.github.axonworkflow.workflow;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.service.StateMachineService;

import java.util.Collections;

// based on https://github.com/spring-projects/spring-statemachine/blob/master/spring-statemachine-samples/datapersist/src/main/java/demo/datapersist/StateMachineController.java

public class WorkflowEngine<S extends Enum<S>, E extends Enum<E>> {

    private StateMachineService<S, E> stateMachineService;

    public WorkflowEngine(StateMachineService<S, E> stateMachineService) {
        this.stateMachineService = stateMachineService;
    }

    boolean handleWorkflowEvent(WorkflowEvent<E> workflowEvent, WorkflowListenerCallback<S, E> callback) {
        String flowId = workflowEvent.getFlowId();
        StateMachine<S, E> stateMachine = loadStateMachine(flowId, callback);
        boolean accepted = stateMachine.sendEvent(MessageBuilder.createMessage(workflowEvent.getEventEnum(),
                new MessageHeaders(Collections.singletonMap(WorkflowConstants.AXON_EVENT, workflowEvent))));
        saveStateMachine(stateMachine);
        return accepted;

    }

    private StateMachine<S, E> loadStateMachine(String flowId, WorkflowListenerCallback<S, E> callback) {
        StateMachine<S, E> seStateMachine = stateMachineService.acquireStateMachine(flowId);
        addListener(seStateMachine, callback);
        seStateMachine.start();
        return seStateMachine;
    }

    private void saveStateMachine(StateMachine<S, E> seStateMachine) {
        stateMachineService.releaseStateMachine(seStateMachine.getId(), false);
    }

    private void addListener(StateMachine<S, E> stateMachine, WorkflowListenerCallback<S, E> callback) {
        stateMachine.addStateListener(new StateMachineListenerAdapter<S, E>() {
            @Override
            public void stateMachineStopped(StateMachine<S, E> stateMachine) {
                callback.workflowCompleted();
            }
        });
    }

}
