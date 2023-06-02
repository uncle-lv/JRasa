package io.github.jrasa.tracker;

import io.github.jrasa.common.BeanUtils;
import io.github.jrasa.common.ReversibleArrayList;
import io.github.jrasa.event.Event;
import io.github.jrasa.event.UserUttered;

import java.util.ArrayList;

public class Tracker {
    private static final String SHOULD_NOT_BE_SET = "should_not_be_set";

    /** ID of the source of the messages */
    private final String senderId;

    /** The currently set values of the slots */
    private final Slots slots;

    /** The most recent message sent by the user */
    private final Message latestMessage;

    /** List of previously seen events */
    private final ReversibleArrayList<Event> events;

    /** Whether the tracker is currently paused */
    private final Boolean paused;

    /** A deterministically scheduled action to be executed next */
    private final String followupAction;

    /** The loop that is currently active */
    private final ActiveLoop activeLoop;

    /** The name of the previously executed action or text of e2e action */
    private final String latestActionName;

    public String getStringSlot(String key) {
        return this.getSlot(key, String.class);
    }

    public Boolean getBoolSlot(String key) {
        return this.getSlot(key, Boolean.class);
    }

    public Double getDoubleSlot(String key) {
        return this.getSlot(key, Double.class);
    }

    public Object getSlot(String key) {
        return this.getSlot(key, Object.class);
    }

    public <T> T getSlot(String key, Class<T> type) {
        if (null == this.slots || !this.slots.containsKey(key)) {
            return null;
        }
        return type.cast(this.slots.get(key));
    }

    public String getLatestInputChannel() {
        for (Event event : this.events.reversed()) {
            if ("user".equals(event.getEvent())) {
                UserUttered userUttered = (UserUttered) event;
                return userUttered.getInputChannel();
            }
        }
        return null;
    }

    /**
     * Return the current tracker state as a {@link TrackerState}.
     *
     * @return a {@link TrackerState} instance.
     */
    public TrackerState getCurrentState() {
        return new TrackerState(
                this.senderId, BeanUtils.deepCopy(this.slots), BeanUtils.deepCopy(this.latestMessage),
                new ArrayList<>(this.events), this.isPaused(), this.getLatestInputChannel(),
                this.followupAction, BeanUtils.deepCopy(this.activeLoop), this.latestActionName);
    }

    /**
     * Create a {@link Tracker} from {@link TrackerState}.
     *
     * @param trackerState A {@link TrackerState} from request
     * @return A {@link Tracker} instance
     */
    public static Tracker createTracker(TrackerState trackerState) {
        return new Tracker(
                trackerState.getSenderId(),
                null == trackerState.getSlots() ? new Slots() : trackerState.getSlots(),
                null == trackerState.getLatestMessage() ? new Message() : trackerState.getLatestMessage(),
                null == trackerState.getEvents() ? new ReversibleArrayList<>() : new ReversibleArrayList<>(trackerState.getEvents()),
                trackerState.isPaused(), trackerState.getFollowupAction(), trackerState.getActiveLoop(),
                trackerState.getLatestActionName()
        );
    }

    private Tracker(String senderId, Slots slots, Message latestMessage, ReversibleArrayList<Event> events, Boolean paused, String followupAction, ActiveLoop activeLoop, String latestActionName) {
        this.senderId = senderId;
        this.slots = slots;
        this.latestMessage = latestMessage;
        this.events = events;
        this.paused = paused;
        this.followupAction = followupAction;
        this.activeLoop = activeLoop;
        this.latestActionName = latestActionName;
    }

    public String getActiveLoopName() {
        if (null == this.activeLoop) {
            return null;
        }

        if (SHOULD_NOT_BE_SET.equals(this.activeLoop.getName())) {
            return null;
        }

        return this.activeLoop.getName();
    }

    public boolean isPaused() {
        return null != this.paused && this.paused;
    }

    public Message getLatestMessage() {
        return BeanUtils.deepCopy(this.latestMessage);
    }
}
