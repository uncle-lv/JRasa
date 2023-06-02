package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestartedTest {
    private static final String JSON = "{" +
              "\"event\": \"restart\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(Restarted.class, event);
        Assertions.assertEquals(Restarted.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        Restarted event = OBJECT_MAPPER.readValue(JSON, Restarted.class);
        Restarted restarted = new Restarted(1647918747.678634);
        Assertions.assertEquals(event, restarted);
    }
}
