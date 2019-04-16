package com.github.axonworkflow.saga;

import com.github.axonworkflow.core.AddParticipantCommand;
import com.github.axonworkflow.core.ApprovalFinishedEvent;
import com.github.axonworkflow.workflow.AbstractWorkflowConfigurer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

import static com.github.axonworkflow.saga.ApprovalEvents.FinishApproval;
import static com.github.axonworkflow.saga.ApprovalEvents.StartApproval;
import static com.github.axonworkflow.saga.ApprovalStates.*;

@Configuration
public class ApprovalWorkflowConfig extends AbstractWorkflowConfigurer<ApprovalStates, ApprovalEvents> {

    @Autowired
    private CommandGateway commandGateway;

    @Override
    public void configure(StateMachineStateConfigurer<ApprovalStates, ApprovalEvents> states) throws Exception {
        states.withStates()
                .initial(Initial)
                .end(Finished)
                .states(EnumSet.allOf(ApprovalStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApprovalStates, ApprovalEvents> transitions) throws Exception {
        //@formatter:off
        transitions

                // Initial =[StartApproval]=> Started
                .withExternal()
                    .source(Initial)
                    .target(Started)
                    .event(StartApproval)

                // Started =[FinishApproval]=> Finished
                .and().withExternal()
                    .source(Started)
                    .target(Finished)
                    .event(FinishApproval)
                    .action(context -> {
                        // Get the original Axon event
                        ApprovalFinishedEvent axonEvent = getAxonEvent(context, ApprovalFinishedEvent.class);
                        // Send command to ChatRoom aggregate to actually add the participant
                        commandGateway.send(new AddParticipantCommand(axonEvent.getRoomId(), axonEvent.getParticipant()));
                    });
        //@formatter:on
    }
}
