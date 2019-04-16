package com.github.axonworkflow.saga;

/**
 * Enumeration of the Saga's states.
 */
public enum ApprovalStates {
    Initial, // state machine started
    Started, // a participant is pending an approval
    Finished // a participant is successfully approved
}
