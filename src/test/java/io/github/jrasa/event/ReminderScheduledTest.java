package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReminderScheduledTest {
    private static final String JSON = "{" +
              "\"event\": \"reminder\"," +
              "\"name\": \"my_reminder\"," +
              "\"intent\": \"my_intent\"," +
              "\"kill_on_user_msg\": true," +
              "\"entities\": [" +
                "{\"entity\": \"entity1\", \"value\": \"value1\"}," +
                "{\"entity\": \"entity2\", \"value\": \"value2\"}" +
              "]," +
              "\"date_time\": \"2018-09-03T11:41:10.128172\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final String SINGLE_ENTITY_JSON = "{" +
              "\"event\": \"reminder\"," +
              "\"name\": \"my_reminder\"," +
              "\"intent\": \"my_intent\"," +
              "\"entities\": {\"entity\": \"entity1\", \"value\": \"value1\"}," +
              "\"kill_on_user_msg\": true," +
              "\"date_time\": \"2018-09-03T11:41:10.128172\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(ReminderScheduled.class, event);
        Assertions.assertEquals(ReminderScheduled.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        ReminderScheduled reminderScheduled = (ReminderScheduled) event;
        Assertions.assertEquals("my_reminder", reminderScheduled.getName());
        Assertions.assertEquals("my_intent", reminderScheduled.getIntentName());
        Assertions.assertTrue(reminderScheduled.getKillOnUserMessage());
        Assertions.assertEquals(2, reminderScheduled.getEntities().size());
        Assertions.assertEquals("entity1", reminderScheduled.getEntities().get(0).getEntity());
        Assertions.assertEquals("value1", reminderScheduled.getEntities().get(0).getValue());
        Assertions.assertEquals("entity2", reminderScheduled.getEntities().get(1).getEntity());
        Assertions.assertEquals("value2", reminderScheduled.getEntities().get(1).getValue());
        Assertions.assertEquals(LocalDateTime.parse(
                "2018-09-03T11:41:10.128172",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME), reminderScheduled.getTriggerDateTime()
        );

        Event singleEntityEvent = OBJECT_MAPPER.readValue(SINGLE_ENTITY_JSON, Event.class);
        Assertions.assertInstanceOf(ReminderScheduled.class, singleEntityEvent);
        Assertions.assertEquals(ReminderScheduled.NAME, singleEntityEvent.getEvent());
        Assertions.assertEquals(1647918747.678634, singleEntityEvent.getTimestamp());
        ReminderScheduled singleEntityReminderScheduled = (ReminderScheduled) singleEntityEvent;
        Assertions.assertEquals("my_reminder", singleEntityReminderScheduled.getName());
        Assertions.assertTrue(singleEntityReminderScheduled.getKillOnUserMessage());
        Assertions.assertEquals("my_intent", singleEntityReminderScheduled.getIntentName());
        Assertions.assertEquals("entity1", singleEntityReminderScheduled.getEntities().get(0).getEntity());
        Assertions.assertEquals("value1", singleEntityReminderScheduled.getEntities().get(0).getValue());
        Assertions.assertEquals(LocalDateTime.parse(
                "2018-09-03T11:41:10.128172",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME), singleEntityReminderScheduled.getTriggerDateTime()
        );
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        ReminderScheduled event = OBJECT_MAPPER.readValue(JSON, ReminderScheduled.class);
        ReminderScheduled reminderScheduled = ReminderScheduled.builder("my_intent")
                .name("my_reminder")
                .killOnUserMessage(true)
                .entities(new ArrayList<Entity>(){{
                    add(Entity.builder().entity("entity1", "value1").build());
                    add(Entity.builder().entity("entity2", "value2").build());
                }})
                .triggerDateTime(LocalDateTime.parse("2018-09-03T11:41:10.128172", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .timestamp(1647918747.678634)
                .build();
        Assertions.assertEquals(event, reminderScheduled);
    }
}
