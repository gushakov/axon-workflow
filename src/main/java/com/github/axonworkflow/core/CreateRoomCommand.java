package com.github.axonworkflow.core;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateRoomCommand {
  @TargetAggregateIdentifier
  private final String roomId;

  private final String name;
}
