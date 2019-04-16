package com.github.axonsimple.workflow;

public interface WorkflowEvent<E extends Enum<E>> {

    E getEventEnum();

    String getFlowId();

}
