package com.github.axonsimple.core;

import com.github.axonsimple.saga.ApprovalEvents;
import com.github.axonsimple.workflow.WorkflowEvent;
import lombok.Value;

@Value
public class ApprovalFinishedEvent implements WorkflowEvent<ApprovalEvents> {

    private final String roomId;

    private final String participant;

    @Override
    public ApprovalEvents getEventEnum() {
        return ApprovalEvents.FinishApproval;
    }

    @Override
    public String getFlowId() {
        return roomId + "~" + participant;
    }
}
