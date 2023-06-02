package io.github.jrasa.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class MessageTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testSerialize() throws JsonProcessingException {
        Message message = Message.builder()
                .text("Hello")
                .image("https://i.imgur.com/nGF1K8f.jpg")
                .response("utter_greet")
                .attachment("")
                .kwargs(new HashMap<String, Object>(){{
                    put("name", "uncle-lv");
                }})
                .build();
        Assertions.assertEquals(
                "{" +
                            "\"text\":\"Hello\"," +
                            "\"image\":\"https://i.imgur.com/nGF1K8f.jpg\"," +
                            "\"custom\":{}," +
                            "\"response\":\"utter_greet\"," +
                            "\"attachment\":\"\"," +
                            "\"buttons\":[]," +
                            "\"elements\":[]," +
                            "\"name\":\"uncle-lv\"" +
                        "}",
                OBJECT_MAPPER.writeValueAsString(message)
        );
    }
}
