package com.github.axonworkflow.core;

import com.github.axonworkflow.saga.ApprovalEvents;
import com.github.axonworkflow.workflow.WorkflowEvent;
import lombok.Value;

@Value
public class ApprovalStartedEvent implements WorkflowEvent<ApprovalEvents> {

    private final String roomId;

    private final String participant;

    @Override
    public ApprovalEvents getEventEnum() {
        return ApprovalEvents.StartApproval;
    }

    @Override
    public String getFlowId() {
        return roomId + "~" + participant;
    }
}
