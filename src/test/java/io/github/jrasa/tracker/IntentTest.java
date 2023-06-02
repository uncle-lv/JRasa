package io.github.jrasa.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntentTest {
    private static final String JSON = "{" +
              "\"name\": \"greet\"," +
              "\"confidence\": 0.9865543842315674" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Intent intent = OBJECT_MAPPER.readValue(JSON, Intent.class);
        Assertions.assertEquals("greet", intent.getName());
        Assertions.assertEquals(0.9865543842315674, intent.getConfidence());
        Assertions.assertEquals("Intent(name=greet, confidence=0.9865543842315674)",intent.toString());
    }
}
