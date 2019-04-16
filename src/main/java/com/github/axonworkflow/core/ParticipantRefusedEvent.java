package com.github.axonworkflow.core;

import lombok.Value;

@Value
public class ParticipantRefusedEvent {

    private final String roomId;

    private final String participant;

}
