package com.github.axonsimple.core;

/**
 * Interface for events intervening into the approval saga.
 */
public interface ParticipantApprovalEvent {

    /**
     * ID of {@link com.github.axonsimple.command.ChatRoom} aggregate issuing this event
     * @return ID of the aggregate
     */
    String roomId();
}
