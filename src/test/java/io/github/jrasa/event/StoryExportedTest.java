package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StoryExportedTest {
    private static final String JSON = "{" +
              "\"event\": \"export\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(StoryExported.class, event);
        Assertions.assertEquals(StoryExported.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        StoryExported event = OBJECT_MAPPER.readValue(JSON, StoryExported.class);
        StoryExported storyExported = new StoryExported(1647918747.678634);
        Assertions.assertEquals(event, storyExported);
    }
}
