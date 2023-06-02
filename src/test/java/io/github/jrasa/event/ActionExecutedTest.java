package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionExecutedTest {
    private static final String JSON = "{" +
              "\"event\": \"action\"," +
              "\"timestamp\": 1647918747.678634," +
              "\"name\": \"utter_greet\"," +
              "\"policy\": null," +
              "\"confidence\": null" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(ActionExecuted.class, event);
        Assertions.assertEquals(ActionExecuted.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        ActionExecuted actionExecuted = (ActionExecuted) event;
        Assertions.assertEquals("utter_greet", actionExecuted.getActionName());
        Assertions.assertNull(actionExecuted.getPolicy());
        Assertions.assertNull(actionExecuted.getConfidence());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        ActionExecuted event = OBJECT_MAPPER.readValue(JSON, ActionExecuted.class);
        ActionExecuted actionExecuted = ActionExecuted.builder("utter_greet")
                .timestamp(1647918747.678634)
                .policy(null)
                .confidence(null)
                .build();
        Assertions.assertEquals(event, actionExecuted);
    }
}
