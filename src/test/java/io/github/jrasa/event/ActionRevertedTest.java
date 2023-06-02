package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionRevertedTest {
    private static final String JSON = "{" +
              "\"event\": \"undo\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(ActionReverted.class, event);
        Assertions.assertEquals(ActionReverted.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        ActionReverted event = OBJECT_MAPPER.readValue(JSON, ActionReverted.class);
        ActionReverted actionReverted = new ActionReverted(1647918747.678634);
        Assertions.assertEquals(event, actionReverted);
    }
}
