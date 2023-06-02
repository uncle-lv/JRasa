package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionExecutionRejectedTest {
    private static final String JSON = "{" +
              "\"event\": \"action_execution_rejected\"," +
              "\"timestamp\": 1647918747.678634," +
              "\"name\": \"utter_greet\"," +
              "\"policy\": null," +
              "\"confidence\": null" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(ActionExecutionRejected.class, event);
        Assertions.assertEquals(ActionExecutionRejected.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        ActionExecutionRejected actionExecutionRejected = (ActionExecutionRejected) event;
        Assertions.assertEquals("utter_greet", actionExecutionRejected.getActionName());
        Assertions.assertNull(actionExecutionRejected.getPolicy());
        Assertions.assertNull(actionExecutionRejected.getConfidence());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        ActionExecutionRejected event = OBJECT_MAPPER.readValue(JSON, ActionExecutionRejected.class);
        ActionExecutionRejected actionExecutionRejected = ActionExecutionRejected.builder("utter_greet")
                .timestamp(1647918747.678634)
                .policy(null)
                .confidence(null)
                .build();
        Assertions.assertEquals(event, actionExecutionRejected);
    }
}
