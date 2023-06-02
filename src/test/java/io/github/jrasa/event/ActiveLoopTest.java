package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActiveLoopTest {
    private static final String JSON = "{" +
              "\"event\": \"active_loop\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(ActiveLoop.class, event);
        Assertions.assertEquals(ActiveLoop.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        ActiveLoop event = OBJECT_MAPPER.readValue(JSON, ActiveLoop.class);
        ActiveLoop activeLoop = new ActiveLoop(1647918747.678634);
        Assertions.assertEquals(event, activeLoop);
    }
}
