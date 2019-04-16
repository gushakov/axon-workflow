package com.github.axonsimple.core;

import com.github.axonsimple.saga.ApprovalEvents;
import com.github.axonsimple.workflow.WorkflowEvent;
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
