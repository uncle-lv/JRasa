package io.github.jrasa;

import io.github.jrasa.domain.Domain;
import io.github.jrasa.event.Event;
import io.github.jrasa.exception.RejectExecuteException;
import io.github.jrasa.tracker.Tracker;

import java.util.Collections;
import java.util.List;

/**
 * Next action to be taken in response to a dialogue state.
 *
 * @author uncle-lv
 */
public interface Action {
    /**
     * Unique identifier of this action.
     *
     * @return Unique identifier of this simple action.
     */
    String name();

    /**
     * Execute the side effects of this action.
     *
     * @param dispatcher the dispatcher which is used to send messages back to the user.
     *                   Use  {@link CollectingDispatcher#utterMessage}  for sending messages.
     * @param tracker the state tracker for the current user.
     *                You can access slot values using {@link Tracker#getSlot(String key)},
     *                the most recent user message is {@link Tracker#getLatestMessage()} and any other {@link Tracker} property.
     * @param domain the bot's domain.
     * @return A list of {@link Event} instances that is returned through the endpoint.
     */
    List<? extends Event> run(CollectingDispatcher dispatcher, Tracker tracker, Domain domain) throws RejectExecuteException;

    /**
     * Returns an empty list (immutable).
     * Using it when you no {@link Event} is returned.
     *
     * @return An empty immutable list.
     */
    static List<Event> empty() {
        return Collections.emptyList();
    }
}
