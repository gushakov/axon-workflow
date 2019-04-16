package com.github.axonworkflow.core;

/**
 * Interface for events intervening into the approval saga.
 */
public interface ParticipantApprovalEvent {

    /**
     * ID of {@link com.github.axonworkflow.command.ChatRoom} aggregate issuing this event
     * @return ID of the aggregate
     */
    String roomId();
}
