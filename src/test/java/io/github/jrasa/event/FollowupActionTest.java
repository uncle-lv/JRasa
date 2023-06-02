package io.github.jrasa.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FollowupActionTest {
    private static final String JSON = "{" +
              "\"event\": \"followup\"," +
              "\"name\": \"action_listen\"," +
              "\"timestamp\": 1647918747.678634" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Event event = OBJECT_MAPPER.readValue(JSON, Event.class);
        Assertions.assertInstanceOf(FollowupAction.class, event);
        Assertions.assertEquals(FollowupAction.NAME, event.getEvent());
        Assertions.assertEquals(1647918747.678634, event.getTimestamp());
        FollowupAction followupAction = (FollowupAction) event;
        Assertions.assertEquals("action_listen", followupAction.getName());
    }

    @Test
    public void testSerialize() throws JsonProcessingException {
        FollowupAction event = OBJECT_MAPPER.readValue(JSON, FollowupAction.class);
        FollowupAction followupAction = new FollowupAction("action_listen", 1647918747.678634);
        Assertions.assertEquals(event, followupAction);
    }
}
