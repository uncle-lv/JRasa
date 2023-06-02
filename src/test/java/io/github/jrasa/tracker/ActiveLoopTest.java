package io.github.jrasa.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActiveLoopTest {
    protected static final String ACTIVE_FORM_JSON = "{" +
              "\"name\": \"restaurant_form\"" +
            "}";

    private static final String NON_ACTIVE_FORM_JSON = "{" +
              "\"name\": \"\"" +
            "}";

    private static final String EMPTY_JSON = "{}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        ActiveLoop activeLoop = OBJECT_MAPPER.readValue(ACTIVE_FORM_JSON, ActiveLoop.class);
        Assertions.assertEquals("restaurant_form", activeLoop.getName());
        Assertions.assertTrue(activeLoop.isActive());
        Assertions.assertEquals("ActiveLoop(name=restaurant_form)", activeLoop.toString());

        ActiveLoop nonActiveLoop = OBJECT_MAPPER.readValue(NON_ACTIVE_FORM_JSON, ActiveLoop.class);
        Assertions.assertEquals("", nonActiveLoop.getName());
        Assertions.assertFalse(nonActiveLoop.isActive());

        ActiveLoop emptyLoop = OBJECT_MAPPER.readValue(EMPTY_JSON, ActiveLoop.class);
        Assertions.assertNull(emptyLoop.getName());
        Assertions.assertFalse(emptyLoop.isActive());
    }
}

