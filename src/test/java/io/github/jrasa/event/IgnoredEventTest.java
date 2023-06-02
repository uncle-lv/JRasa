package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IgnoredEventTest {
    private static final String JSON = "{" +
              "\"event\": \"user_featurization\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(IgnoredEvent.class, event);
        Assertions.assertEquals("user_featurization", event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }
}
