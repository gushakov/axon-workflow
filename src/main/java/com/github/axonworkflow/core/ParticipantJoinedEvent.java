package com.github.axonworkflow.core;

import lombok.Value;

@Value
public class ParticipantJoinedEvent {

    private final String roomId;

    private final String participant;

}
