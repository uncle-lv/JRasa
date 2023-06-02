package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoopInterruptedTest {
    private static final String JSON = "{" +
              "\"event\": \"loop_interrupted\"," +
              "\"is_interrupted\": false," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(LoopInterrupted.class, event);
        Assertions.assertEquals(LoopInterrupted.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        LoopInterrupted loopInterrupted = (LoopInterrupted) event;
        Assertions.assertFalse(loopInterrupted.isInterrupted());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        LoopInterrupted event = OBJECT_MAPPER.readValue(JSON, LoopInterrupted.class);
        LoopInterrupted loopInterrupted = new LoopInterrupted(false, 1647918747.678634);
        Assertions.assertEquals(event, loopInterrupted);
    }
}
