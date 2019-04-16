package com.github.axonsimple.saga;

import com.github.axonsimple.core.ApprovalStartedEvent;
import com.github.axonsimple.workflow.AbstractWorkflowSagaAdapter;
import org.axonframework.spring.stereotype.Saga;

@Saga
public class ApprovalSaga extends AbstractWorkflowSagaAdapter<ApprovalStates, ApprovalEvents, ApprovalStartedEvent> {
}
