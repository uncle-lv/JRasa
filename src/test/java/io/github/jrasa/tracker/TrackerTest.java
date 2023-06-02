package io.github.jrasa.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TrackerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static Tracker tracker;
    private static TrackerState trackerState;

    @BeforeAll
    public static void init() throws JsonProcessingException {
        trackerState = OBJECT_MAPPER.readValue(TrackerStateTest.JSON, TrackerState.class);
        tracker = Tracker.createTracker(trackerState);
    }

    @Test
    public void testCreateTracker() {
        Assertions.assertNull(tracker.getStringSlot("restaurant_name"));
        Assertions.assertEquals("Italian", tracker.getStringSlot("cuisine"));
        Assertions.assertEquals(4.0, tracker.getDoubleSlot("num_people"));
        Assertions.assertTrue(tracker.getBoolSlot("outdoor_seating"));
        Assertions.assertEquals("cmdline", tracker.getLatestInputChannel());
        Assertions.assertEquals("restaurant_form", tracker.getActiveLoopName());
        Assertions.assertFalse(tracker.isPaused());
        Assertions.assertEquals(trackerState.getLatestMessage(), tracker.getLatestMessage());
        Assertions.assertEquals(trackerState, tracker.getCurrentState());
    }
}
