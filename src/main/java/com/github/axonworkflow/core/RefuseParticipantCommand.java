package com.github.axonworkflow.core;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class RefuseParticipantCommand {

    @TargetAggregateIdentifier
    private final String roomId;

    private final String participant;

}
