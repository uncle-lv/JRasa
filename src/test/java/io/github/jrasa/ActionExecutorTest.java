package io.github.jrasa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jrasa.domain.Domain;
import io.github.jrasa.event.Event;
import io.github.jrasa.exception.ActionNotFoundException;
import io.github.jrasa.exception.RejectExecuteException;
import io.github.jrasa.message.Message;
import io.github.jrasa.rest.ActionCall;
import io.github.jrasa.rest.ActionResponse;
import io.github.jrasa.tracker.Tracker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

public class ActionExecutorTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String TEST_ACTION = "{" +
              "\"next_action\": \"action_test\",\n" +
              "\"sender_id\": \"uncle-lv\",\n" +
              "\"tracker\": {},\n" +
              "\"version\": \"3.5.4\",\n" +
              "\"domain\": {}\n" +
            "}";

    private static final String NOT_EXISTED_ACTION = "{" +
              "\"next_action\": \"action_not_existed\",\n" +
              "\"sender_id\": \"uncle-lv\",\n" +
              "\"tracker\": {},\n" +
              "\"version\": \"3.5.4\",\n" +
              "\"domain\": {}\n" +
            "}";

    private static final String REJECT_EXECUTE_ACTION = "{" +
              "\"next_action\": \"action_reject_execute\",\n" +
              "\"sender_id\": \"uncle-lv\",\n" +
              "\"tracker\": {},\n" +
              "\"version\": \"3.5.4\",\n" +
              "\"domain\": {}\n" +
            "}";

    private static final String RETURN_NULL_ACTION = "{" +
              "\"next_action\": \"action_return_null\",\n" +
              "\"sender_id\": \"uncle-lv\",\n" +
              "\"tracker\": {},\n" +
              "\"version\": \"3.5.4\",\n" +
              "\"domain\": {}\n" +
            "}";

    private ActionExecutor actionExecutor;

    @BeforeEach
    public void init() {
        actionExecutor = new ActionExecutor();
        actionExecutor.registerAction(new TestAction());
        actionExecutor.registerAction(new RejectExecuteAction());
        // re-registered action
        actionExecutor.registerAction(new RejectExecuteAction());
        actionExecutor.registerAction(new ReturnNullAction());
    }

    @Test
    public void testRun() throws JsonProcessingException {
        ActionCall actionCall = OBJECT_MAPPER.readValue(TEST_ACTION, ActionCall.class);
        ActionResponse response;
        try {
            response = actionExecutor.run(actionCall);
        } catch (ActionNotFoundException | RejectExecuteException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals("Hello", response.getMessages().get(0).getText());
        Assertions.assertEquals(0, response.getEvents().size());
    }

    @Test
    public void testNotExisted() throws JsonProcessingException {
        ActionCall actionCall = OBJECT_MAPPER.readValue(NOT_EXISTED_ACTION, ActionCall.class);
        Assertions.assertThrows(ActionNotFoundException.class, () -> actionExecutor.run(actionCall));
    }

    @Test
    public void testRejectExecute() throws JsonProcessingException {
        ActionCall actionCall = OBJECT_MAPPER.readValue(REJECT_EXECUTE_ACTION, ActionCall.class);
        Assertions.assertThrows(RejectExecuteException.class, () -> actionExecutor.run(actionCall));
    }

    @Test
    public void testReturnNull() throws JsonProcessingException {
        ActionCall actionCall = OBJECT_MAPPER.readValue(RETURN_NULL_ACTION, ActionCall.class);
        ActionResponse response;
        try {
            response = actionExecutor.run(actionCall);
        } catch (ActionNotFoundException | RejectExecuteException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(response.getEvents());
        Assertions.assertEquals(0, response.getEvents().size());
    }

    @Test
    public void testUnnamedAction() {
        ActionCall actionCall = new ActionCall();
        ActionResponse response;
        try {
            response = actionExecutor.run(actionCall);
        } catch (ActionNotFoundException | RejectExecuteException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNull(response);
    }

    @Test
    public void testGetRegisteredActions() {
        List<String> actions = actionExecutor.getRegisteredActions();
        Assertions.assertEquals(3, actions.size());
        Assertions.assertEquals(new HashSet<String>(){{
            add("action_test");
            add("action_reject_execute");
            add("action_return_null");
        }}, new HashSet<>(actions));
    }

    private static class TestAction implements Action {

        @Override
        public String name() {
            return "action_test";
        }

        @Override
        public List<Event> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
            dispatcher.utterMessage(
                    Message.builder()
                            .text("Hello")
                            .build()
            );
            return Action.empty();
        }
    }

    private static class RejectExecuteAction implements Action {

        @Override
        public String name() {
            return "action_reject_execute";
        }

        @Override
        public List<Event> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) throws RejectExecuteException {
            throw new RejectExecuteException(this.name());
        }
    }

    private static class ReturnNullAction implements Action {

        @Override
        public String name() {
            return "action_return_null";
        }

        @Override
        public List<Event> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) {
            return null;
        }
    }
}
