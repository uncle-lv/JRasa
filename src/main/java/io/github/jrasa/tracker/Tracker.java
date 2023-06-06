package io.github.jrasa.tracker;

import io.github.jrasa.common.BeanUtils;
import io.github.jrasa.common.ReversibleArrayList;
import io.github.jrasa.event.Event;
import io.github.jrasa.event.SlotSet;
import io.github.jrasa.event.UserUttered;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tracker {
    private static final String SHOULD_NOT_BE_SET = "should_not_be_set";
    private static final String NLU_FALLBACK_INTENT_NAME = "nlu_fallback";

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

    public void updateSlots(Map<String, Object> slots) {
        this.slots.putAll(slots);
    }

    /**
     * Get slots which were recently set.
     * This can e.g. be used to validate form slots after they were extracted.
     *
     * @return A mapping of extracted slot candidates and their values.
     */
    public Map<String, Object> slotsToValidate() {
        Map<String, Object> slots = new HashMap<>();
        int cnt = 0;
        for (Event event : this.events.reversed()) {
            if ("slot".equals(event.getEvent())) {
                cnt += 1;
            } else {
                break;
            }
        }

        for (Event event : this.events.subList(this.events.size() - cnt, this.events.size())) {
            SlotSet slotSet = (SlotSet) event;
            slots.put(slotSet.getName(), slotSet.getValue());
        }
        
        return slots;
    }

    /**
     * Adds slots to the current tracker.
     *
     * @param slots {@link SlotSet} events.
     */
    public void addSlots(List<SlotSet> slots) {
        for (SlotSet slotSet : slots) {
            this.slots.put(slotSet.getName(), slotSet.getValue());
            this.events.add(slotSet);
        }
    }

    public String getIntentOfLatestMessage() {
        return this.getIntentOfLatestMessage(true);
    }

    /**
     * Retrieves the intent the last user message.
     *
     * @param skipFallbackIntent Optionally skip the nlu_fallback intent and return the next.
     * @return Intent of latest message if available.
     */
    public String getIntentOfLatestMessage(boolean skipFallbackIntent) {
        Message latestMessage = this.latestMessage;
        if (null == latestMessage) {
            return null;
        }

        List<Intent> intentRanking = latestMessage.getIntentRanking();
        if (null == intentRanking || intentRanking.isEmpty()) {
            return null;
        }

        Intent highestRankingIntent = intentRanking.get(0);
        if (NLU_FALLBACK_INTENT_NAME.equals(highestRankingIntent.getName()) && skipFallbackIntent) {
            if (intentRanking.size() >= 2) {
                return intentRanking.get(1).getName();
            } else {
                return null;
            }
        } else {
            return highestRankingIntent.getName();
        }
    }
}
