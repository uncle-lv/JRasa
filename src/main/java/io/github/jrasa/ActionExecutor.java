package io.github.jrasa;

import io.github.jrasa.common.StringUtils;
import io.github.jrasa.domain.Domain;
import io.github.jrasa.event.Event;
import io.github.jrasa.exception.ActionNotFoundException;
import io.github.jrasa.exception.RejectExecuteException;
import io.github.jrasa.rest.ActionCall;
import io.github.jrasa.rest.ActionResponse;
import io.github.jrasa.tracker.Tracker;
import io.github.jrasa.tracker.TrackerState;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ActionExecutor {
    private final Map<String, Action> actions = new HashMap<>();

    public ActionResponse run(ActionCall actionCall) throws ActionNotFoundException, RejectExecuteException {
        String actionName = actionCall.getNextAction();
        if (!StringUtils.isNullOrEmpty(actionName)) {
            log.info("Received request to run '{}'", actionName);
            Action action = this.actions.get(actionName);
            if (null == action) {
                throw new ActionNotFoundException(actionName);
            }

            TrackerState trackerState = actionCall.getTracker();
            Tracker tracker = Tracker.createTracker(trackerState);
            Domain domain = actionCall.getDomain();
            CollectingDispatcher dispatcher = new CollectingDispatcher();
            List<? extends Event> events = action.run(dispatcher, tracker, domain);
            if (null == events) {
                events = Collections.emptyList();
            }
            log.debug("Finished running '{}'", actionName);
            return new ActionResponse(events, null == dispatcher.getMessages() ? Collections.emptyList() : dispatcher.getMessages());
        }
        log.warn("Received an action call without an action.");
        return null;
    }

    public void registerAction(Action action) {
        if (actions.containsKey(action.name())) {
            log.warn("Re-registered action for '{}'.", action.name());
        } else {
            log.info("Registered action for '{}'.", action.name());
        }
        actions.put(action.name(), action);
    }

    public List<String> getRegisteredActions() {
        return new ArrayList<>(this.actions.keySet());
    }
}
