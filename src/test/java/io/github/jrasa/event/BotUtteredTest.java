package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class BotUtteredTest {
    private static final String JSON = "{" +
              "\"event\": \"bot\"," +
              "\"text\": \"Hi there! How can I help you today?\"," +
              "\"data\": {}," +
              "\"metadata\": {}," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(BotUttered.class, event);
        Assertions.assertEquals(BotUttered.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        BotUttered botUttered = (BotUttered) event;
        Assertions.assertEquals("Hi there! How can I help you today?", botUttered.getText());
        Assertions.assertEquals(Collections.emptyMap(), botUttered.getData());
        Assertions.assertEquals(Collections.emptyMap(), botUttered.getMetaData());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        BotUttered event = OBJECT_MAPPER.readValue(JSON, BotUttered.class);
        BotUttered botUttered = BotUttered.builder()
                .text("Hi there! How can I help you today?")
                .data(Collections.emptyMap())
                .metaData(Collections.emptyMap())
                .timestamp(1647918747.678634)
                .build();
        Assertions.assertEquals(event, botUttered);
    }
}
