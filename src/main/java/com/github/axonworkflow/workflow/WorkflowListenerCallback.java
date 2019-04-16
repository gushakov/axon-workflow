package com.github.axonworkflow.workflow;

public interface WorkflowListenerCallback<S extends Enum<S>, E extends Enum<E>> {

    void workflowCompleted();

}
