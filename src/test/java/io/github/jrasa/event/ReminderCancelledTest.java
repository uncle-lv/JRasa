package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ReminderCancelledTest {
    private static final String JSON = "{" +
              "\"event\": \"cancel_reminder\"," +
              "\"name\": \"my_reminder\"," +
              "\"intent\": \"my_intent\"," +
              "\"entities\": [" +
                "{\"entity\": \"entity1\", \"value\": \"value1\"}," +
                "{\"entity\": \"entity2\", \"value\": \"value2\"}" +
              "]," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final String SINGLE_ENTITY_JSON = "{" +
              "\"event\": \"cancel_reminder\"," +
              "\"name\": \"my_reminder\"," +
              "\"intent\": \"my_intent\"," +
              "\"entities\": {\"entity\": \"entity1\", \"value\": \"value1\"}," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(ReminderCancelled.class, event);
        Assertions.assertEquals(ReminderCancelled.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        ReminderCancelled reminderCancelled = (ReminderCancelled) event;
        Assertions.assertEquals("my_reminder", reminderCancelled.getName());
        Assertions.assertEquals("my_intent", reminderCancelled.getIntentName());
        Assertions.assertEquals(2, reminderCancelled.getEntities().size());
        Assertions.assertEquals("entity1", reminderCancelled.getEntities().get(0).getEntity());
        Assertions.assertEquals("value1", reminderCancelled.getEntities().get(0).getValue());
        Assertions.assertEquals("entity2", reminderCancelled.getEntities().get(1).getEntity());
        Assertions.assertEquals("value2", reminderCancelled.getEntities().get(1).getValue());

        Event singleEntityEvent = OBJECT_MAPPER.readValue(SINGLE_ENTITY_JSON, Event.class);
        Assertions.assertInstanceOf(ReminderCancelled.class, singleEntityEvent);
        Assertions.assertEquals(ReminderCancelled.NAME, singleEntityEvent.getEvent());
        Assertions.assertEquals(1647918747.678634, singleEntityEvent.getTimestamp());
        ReminderCancelled singleEntityReminderCancelled = (ReminderCancelled) singleEntityEvent;
        Assertions.assertEquals("my_reminder", singleEntityReminderCancelled.getName());
        Assertions.assertEquals("my_intent", singleEntityReminderCancelled.getIntentName());
        Assertions.assertEquals("entity1", singleEntityReminderCancelled.getEntities().get(0).getEntity());
        Assertions.assertEquals("value1", singleEntityReminderCancelled.getEntities().get(0).getValue());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        ReminderCancelled event = OBJECT_MAPPER.readValue(JSON, ReminderCancelled.class);
        ReminderCancelled reminderCancelled = ReminderCancelled.builder()
                .intentName("my_intent")
                .name("my_reminder")
                .entities(new ArrayList<Entity>(){{
                    add(Entity.builder().entity("entity1", "value1").build());
                    add(Entity.builder().entity("entity2", "value2").build());
                }})
                .timestamp(1647918747.678634)
                .build();
        Assertions.assertEquals(event, reminderCancelled);
    }
}
