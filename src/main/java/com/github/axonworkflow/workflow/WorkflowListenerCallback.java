package com.github.axonworkflow.workflow;

/**
 * Callback to be notified at specific point of time during a workflow execution.
 * @param <S> enumeration of saga's states
 * @param <E> enumeration of Saga's events
 */
public interface WorkflowListenerCallback<S extends Enum<S>, E extends Enum<E>> {

    /**
     * Called when the state machine reaches an end state.
     */
    void workflowCompleted();

}
