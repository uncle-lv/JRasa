package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class UserUtteredTest {
    private static final String JSON = "{" +
              "\"event\": \"user\"," +
              "\"text\": \"Hello\"," +
              "\"parse_data\": {}," +
              "\"input_channel\": \"cmdline\"," +
              "\"message_id\": \"1234567890\"," +
              "\"metadata\": {}," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(UserUttered.class, event);
        Assertions.assertEquals(UserUttered.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        UserUttered userUttered = (UserUttered) event;
        Assertions.assertEquals("Hello", userUttered.getText());
        Assertions.assertEquals(Collections.emptyMap(), userUttered.getParseData());
        Assertions.assertEquals("cmdline", userUttered.getInputChannel());
        Assertions.assertEquals("1234567890", userUttered.getMessageId());
        Assertions.assertEquals(Collections.emptyMap(), userUttered.getMetaData());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        String json = "{" +
                    "\"event\": \"user\"," +
                    "\"text\": \"Hello\"," +
                    "\"parse_data\": {}," +
                    "\"input_channel\": \"rest\"," +
                    "\"message_id\": null," +
                    "\"metadata\": null," +
                    "\"timestamp\": 1647918747.678634" +
                "}";
        UserUttered event = OBJECT_MAPPER.readValue(json, UserUttered.class);
        UserUttered userUttered = UserUttered.builder()
                .text("Hello")
                .inputChannel("rest")
                .parseData(Collections.emptyMap())
                .timestamp(1647918747.678634)
                .build();
        Assertions.assertEquals(event, userUttered);
    }
}
