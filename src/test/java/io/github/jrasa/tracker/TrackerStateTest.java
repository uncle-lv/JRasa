package io.github.jrasa.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrackerStateTest {
    protected static final String JSON = "{" +
              "\"sender_id\": \"uncle-lv\"," +
              "\"slots\": " + SlotsTest.JSON + "," +
              "\"latest_message\": " + MessageTest.JSON + "," +
              "\"events\": [" +
                "{" +
                  "\"event\": \"user\"," +
                  "\"text\": \"Hello\"," +
                  "\"parse_data\": {}," +
                  "\"input_channel\": \"cmdline\"," +
                  "\"message_id\": \"1234567890\"," +
                  "\"metadata\": {}," +
                  "\"timestamp\": 1647918747.678634" +
                "}" +
              "]," +
              "\"paused\": false," +
              "\"latest_input_channel\": \"cmdline\"," +
              "\"followup_action\": null," +
              "\"active_loop\": " + ActiveLoopTest.ACTIVE_FORM_JSON + "," +
              "\"latest_action_name\": \"action_listen\"" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Slots slots = OBJECT_MAPPER.readValue(SlotsTest.JSON, Slots.class);
        Message message = OBJECT_MAPPER.readValue(MessageTest.JSON, Message.class);
        ActiveLoop activeLoop = OBJECT_MAPPER.readValue(ActiveLoopTest.ACTIVE_FORM_JSON, ActiveLoop.class);

        TrackerState trackerState = OBJECT_MAPPER.readValue(JSON, TrackerState.class);
        Assertions.assertEquals("uncle-lv", trackerState.getSenderId());
        Assertions.assertEquals(slots, trackerState.getSlots());
        Assertions.assertEquals(message, trackerState.getLatestMessage());
        Assertions.assertEquals(1, trackerState.getEvents().size());
        Assertions.assertFalse(trackerState.isPaused());
        Assertions.assertEquals("cmdline", trackerState.getLatestInputChannel());
        Assertions.assertNull(trackerState.getFollowupAction());
        Assertions.assertEquals(activeLoop, trackerState.getActiveLoop());
        Assertions.assertEquals("action_listen", trackerState.getLatestActionName());
    }
}
