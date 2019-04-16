package com.github.axonsimple.workflow;

public interface WorkflowListenerCallback<S extends Enum<S>, E extends Enum<E>> {

    void workflowCompleted();

}
