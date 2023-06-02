package io.github.jrasa.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ButtonTest {
    private static final String JSON = "[" +
              "{\"payload\": \"/affirm\", \"title\": \"Yes\"}," +
              "{\"payload\": \"/deny\", \"title\": \"No\"}" +
            "]";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testSerialize() throws JsonProcessingException {
        List<Button> buttons = OBJECT_MAPPER.readValue(JSON, new TypeReference<List<Button>>() {});
        Button affirmButton = new Button("Yes", "/affirm");
        Button denyButton = new Button("No", "/deny");
        Assertions.assertEquals(buttons, new ArrayList<Button>(){{
            add(affirmButton);
            add(denyButton);
        }});
    }
}
