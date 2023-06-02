package io.github.jrasa.tracker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.jrasa.event.Event;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrackerState {
    /** ID of the source of the messages */
    @JsonProperty("sender_id")
    private String senderId;

    /** The currently set values of the slots */
    private Slots slots;

    /** The most recent message sent by the user */
    @JsonProperty("latest_message")
    private Message latestMessage;

    /** List of previously seen events */
    private List<Event> events;

    /** Whether the tracker is currently paused */
    private Boolean paused;

    /** The name of the input_channel of the latest UserUttered event */
    @JsonProperty("latest_input_channel")
    private String latestInputChannel;

    /** A deterministically scheduled action to be executed next */
    @JsonProperty("followup_action")
    private String followupAction;

    /** The loop that is currently active */
    @JsonProperty("active_loop")
    private ActiveLoop activeLoop;

    /** The name of the previously executed action or text of e2e action */
    @JsonProperty("latest_action_name")
    private String latestActionName;

    @JsonIgnore
    public boolean isPaused() {
        return null != this.paused && this.paused;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackerState that = (TrackerState) o;

        if (this.isPaused() != that.isPaused()) return false;
        if (senderId != null ? !senderId.equals(that.senderId) : that.senderId != null) return false;
        if (slots != null ? !slots.equals(that.slots) : that.slots != null) return false;
        if (latestMessage != null ? !latestMessage.equals(that.latestMessage) : that.latestMessage != null)
            return false;
        if (events != null ? !events.equals(that.events) : that.events != null) return false;
        if (latestInputChannel != null ? !latestInputChannel.equals(that.latestInputChannel) : that.latestInputChannel != null)
            return false;
        if (followupAction != null ? !followupAction.equals(that.followupAction) : that.followupAction != null)
            return false;
        if (activeLoop != null ? !activeLoop.equals(that.activeLoop) : that.activeLoop != null) return false;
        return latestActionName != null ? latestActionName.equals(that.latestActionName) : that.latestActionName == null;
    }

    @Override
    public int hashCode() {
        int result = senderId != null ? senderId.hashCode() : 0;
        result = 31 * result + (slots != null ? slots.hashCode() : 0);
        result = 31 * result + (latestMessage != null ? latestMessage.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        result = 31 * result + (this.isPaused() ? 1 : 0);
        result = 31 * result + (latestInputChannel != null ? latestInputChannel.hashCode() : 0);
        result = 31 * result + (followupAction != null ? followupAction.hashCode() : 0);
        result = 31 * result + (activeLoop != null ? activeLoop.hashCode() : 0);
        result = 31 * result + (latestActionName != null ? latestActionName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TrackerState(" +
                "senderId='" + senderId + '\'' +
                ", slots=" + slots +
                ", latestMessage=" + latestMessage +
                ", events=" + events +
                ", paused=" + this.isPaused() +
                ", latestInputChannel='" + latestInputChannel + '\'' +
                ", followupAction='" + followupAction + '\'' +
                ", activeLoop=" + activeLoop +
                ", latestActionName='" + latestActionName + '\'' +
                ')';
    }
}
