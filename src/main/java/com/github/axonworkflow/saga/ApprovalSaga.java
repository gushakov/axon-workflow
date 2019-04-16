package com.github.axonworkflow.saga;

import com.github.axonworkflow.core.ApprovalStartedEvent;
import com.github.axonworkflow.workflow.AbstractWorkflowSagaAdapter;
import org.axonframework.spring.stereotype.Saga;

/**
 * Saga backed by a workflow. Models an approval of a participant attempting to join a chat room.
 * @see ApprovalStates
 * @see ApprovalEvents
 * @see AbstractWorkflowSagaAdapter
 */
@Saga
public class ApprovalSaga extends AbstractWorkflowSagaAdapter<ApprovalStates, ApprovalEvents, ApprovalStartedEvent> {
}
