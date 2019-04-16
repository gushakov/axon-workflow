Axon Saga backed by a Spring State Machine Workflow
---

A proof-of-concept implementation of an Axon Saga backed by a workflow using Spring State Machine.
The idea is to bridge Sagas to a workflows via a generic adapter. The state machine should
be looked up and persisted into JPA database and have the same lifecycle as the corresponding Saga.

#### Links

- [StateMachineConfig.java](https://github.com/spring-projects/spring-statemachine/blob/master/spring-statemachine-samples/datapersist/src/main/java/demo/datapersist/StateMachineConfig.java)
- [StateMachineController.java](https://github.com/spring-projects/spring-statemachine/blob/master/spring-statemachine-samples/datapersist/src/main/java/demo/datapersist/StateMachineController.java)

#### Copyright disclaimer and acknowledgements

This code is based heavily on the code for the [axon-quick-start](https://github.com/AxonIQ/axon-quick-start)
exercise (and the solution).

And the code from Spring Machine [51. Data Persist](https://docs.spring.io/spring-statemachine/docs/current/reference/html/statemachine-examples-datapersist.html)
example.

[PlantText](https://www.planttext.com/) was used to create UML diagrams.