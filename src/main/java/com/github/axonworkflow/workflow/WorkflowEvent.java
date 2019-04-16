package com.github.axonworkflow.workflow;

public interface WorkflowEvent<E extends Enum<E>> {

    E getEventEnum();

    String getFlowId();

}
