package io.github.jrasa.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SlotsTest {
    protected static final String JSON = "{" +
              "\"cuisine\": \"Italian\"," +
              "\"num_people\": 4.0," +
              "\"outdoor_seating\": true" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Slots slots = OBJECT_MAPPER.readValue(JSON, Slots.class);
        Assertions.assertNull(slots.get("restaurant_name"));
        Assertions.assertEquals("Italian", slots.get("cuisine"));
        Assertions.assertEquals(4.0, slots.get("num_people"));
        Assertions.assertTrue((Boolean) slots.get("outdoor_seating"));
        Assertions.assertEquals("{cuisine=Italian, outdoor_seating=true, num_people=4.0}", slots.toString());
    }
}
