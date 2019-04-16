package com.github.axonworkflow.workflow;

/**
 * Generic interface to be implemented by any of Sagas's events used in a workflow. This
 * is a bridge between Axon's events and the workflow events.
 * @param <E> enumeration of Saga's events
 */
public interface WorkflowEvent<E extends Enum<E>> {

    /**
     * Enumeration value corresponding to this Saga's event.
     * @return enumeration value for this event
     */
    E getEventEnum();

    /**
     * ID of the workflow where this event is used.
     * @return workflow ID
     */
    String getFlowId();

}
