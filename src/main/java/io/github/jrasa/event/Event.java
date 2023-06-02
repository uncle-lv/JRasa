package io.github.jrasa.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "event",
        defaultImpl = IgnoredEvent.class,
        visible = true
)
@JsonSubTypes(
        value = {
                @JsonSubTypes.Type(value = UserUttered.class, name = UserUttered.NAME),
                @JsonSubTypes.Type(value = BotUttered.class, name = BotUttered.NAME),
                @JsonSubTypes.Type(value = ActionExecuted.class, name = ActionExecuted.NAME),
                @JsonSubTypes.Type(value = SessionStarted.class, name = SessionStarted.NAME),
                @JsonSubTypes.Type(value = SlotSet.class, name = SlotSet.NAME),
                @JsonSubTypes.Type(value = Restarted.class, name = Restarted.NAME),
                @JsonSubTypes.Type(value = ActionReverted.class, name = ActionReverted.NAME),
                @JsonSubTypes.Type(value = AllSlotsReset.class, name = AllSlotsReset.NAME),
                @JsonSubTypes.Type(value = ConversationPaused.class, name = ConversationPaused.NAME),
                @JsonSubTypes.Type(value = ConversationResumed.class, name = ConversationResumed.NAME),
                @JsonSubTypes.Type(value = FollowupAction.class, name = FollowupAction.NAME),
                @JsonSubTypes.Type(value = UserUtteranceReverted.class, name = UserUtteranceReverted.NAME),
                @JsonSubTypes.Type(value = StoryExported.class, name = StoryExported.NAME),
                @JsonSubTypes.Type(value = AgentUttered.class, name = AgentUttered.NAME),
                @JsonSubTypes.Type(value = ActiveLoop.class, name = ActiveLoop.NAME),
                @JsonSubTypes.Type(value = LoopInterrupted.class, name = LoopInterrupted.NAME),
                @JsonSubTypes.Type(value = ActionExecutionRejected.class, name = ActionExecutionRejected.NAME),
                @JsonSubTypes.Type(value = ReminderScheduled.class, name = ReminderScheduled.NAME),
                @JsonSubTypes.Type(value = ReminderCancelled.class, name = ReminderCancelled.NAME)
        }
)
public abstract class Event {
    protected String event;
    protected Double timestamp;

    public Event(String event) {
        this.event = event;
    }

    public Event(String event, Double timestamp) {
        this.event = event;
        this.timestamp = timestamp;
    }
}
