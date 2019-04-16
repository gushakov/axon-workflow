package com.github.axonworkflow.saga;

import com.github.axonworkflow.core.ApprovalStartedEvent;
import com.github.axonworkflow.workflow.AbstractWorkflowSagaAdapter;
import org.axonframework.spring.stereotype.Saga;

@Saga
public class ApprovalSaga extends AbstractWorkflowSagaAdapter<ApprovalStates, ApprovalEvents, ApprovalStartedEvent> {
}
