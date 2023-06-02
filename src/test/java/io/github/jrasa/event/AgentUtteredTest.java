package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class AgentUtteredTest {
    private static final String JSON = "{" +
              "\"event\": \"agent\"," +
              "\"text\": \"Hello\"," +
              "\"data\": {}," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(AgentUttered.class, event);
        Assertions.assertEquals(AgentUttered.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        AgentUttered agentUttered = (AgentUttered) event;
        Assertions.assertEquals("Hello", agentUttered.getText());
        Assertions.assertEquals(Collections.emptyMap(), agentUttered.getData());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        AgentUttered event = OBJECT_MAPPER.readValue(JSON, AgentUttered.class);
        AgentUttered agentUttered = AgentUttered.builder()
                .text("Hello")
                .data(Collections.emptyMap())
                .timestamp(1647918747.678634)
                .build();
        Assertions.assertEquals(event, agentUttered);
    }
}
