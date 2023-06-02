package io.github.jrasa.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessageTest {
    protected static final String JSON = "{" +
              "\"text\": \"Hello, how can I help you today?\"," +
              "\"intent\": {" +
                "\"name\": \"greeting\"," +
                "\"confidence\": 0.9865543842315674" +
              "}," +
              "\"entities\": []," +
              "\"intent_ranking\": [" +
                "{" +
                  "\"name\": \"greeting\"," +
                  "\"confidence\": 0.9865543842315674" +
                "}," +
                "{" +
                  "\"name\": \"goodbye\"," +
                  "\"confidence\": 0.013445615768432617" +
                "}" +
              "]" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Message message = OBJECT_MAPPER.readValue(JSON, Message.class);
        Assertions.assertEquals("Hello, how can I help you today?", message.getText());
        Assertions.assertEquals("greeting", message.getIntent().getName());
        Assertions.assertEquals(0.9865543842315674, message.getIntent().getConfidence());
        Assertions.assertEquals(0, message.getEntities().size());
        Assertions.assertEquals(2, message.getIntentRanking().size());
        Intent greet = message.getIntentRanking().get(0);
        Assertions.assertEquals("greeting", greet.getName());
        Assertions.assertEquals(0.9865543842315674, greet.getConfidence());
        Intent goodbye = message.getIntentRanking().get(1);
        Assertions.assertEquals("goodbye", goodbye.getName());
        Assertions.assertEquals(0.013445615768432617, goodbye.getConfidence());
        Assertions.assertEquals(
                "Message(intent=Intent(name=greeting, confidence=0.9865543842315674), entities=[], text=Hello, how can I help you today?, intentRanking=[Intent(name=greeting, confidence=0.9865543842315674), Intent(name=goodbye, confidence=0.013445615768432617)])",
                message.toString());
    }
}
