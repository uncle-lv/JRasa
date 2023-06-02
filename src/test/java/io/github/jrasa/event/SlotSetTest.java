package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SlotSetTest {
    private static final String JSON = "{" +
              "\"event\": \"slot\"," +
              "\"name\": \"name\"," +
              "\"value\": \"John\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(SlotSet.class, event);
        Assertions.assertEquals(SlotSet.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        SlotSet event = OBJECT_MAPPER.readValue(JSON, SlotSet.class);
        SlotSet SlotSet = new SlotSet("name", "John", 1647918747.678634);
        Assertions.assertEquals(event, SlotSet);
    }
}
