package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConversationResumedTest {
    private static final String JSON = "{" +
              "\"event\": \"resume\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(ConversationResumed.class, event);
        Assertions.assertEquals(ConversationResumed.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        ConversationResumed event = OBJECT_MAPPER.readValue(JSON, ConversationResumed.class);
        ConversationResumed conversationResumed = new ConversationResumed(1647918747.678634);
        Assertions.assertEquals(event, conversationResumed);
    }
}
