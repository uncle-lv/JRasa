package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserUtteranceRevertedTest {
    private static final String JSON = "{" +
              "\"event\": \"rewind\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(UserUtteranceReverted.class, event);
        Assertions.assertEquals(UserUtteranceReverted.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        UserUtteranceReverted event = OBJECT_MAPPER.readValue(JSON, UserUtteranceReverted.class);
        UserUtteranceReverted userUtteranceReverted = new UserUtteranceReverted(1647918747.678634);
        Assertions.assertEquals(event, userUtteranceReverted);
    }
}
