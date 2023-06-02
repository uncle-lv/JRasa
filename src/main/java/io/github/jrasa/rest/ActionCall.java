package io.github.jrasa.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.jrasa.domain.Domain;
import io.github.jrasa.tracker.TrackerState;
import lombok.Getter;
import lombok.ToString;

/**
 * A representation of an action to be executed.
 *
 * @author uncle-lv
 */
@Getter
@ToString
public class ActionCall {

    /** Rasa version */
    private String version;

    /** The name of the next action to be executed */
    @JsonProperty("next_action")
    private String nextAction;

    /** ID of the source of the messages */
    @JsonProperty("sender_id")
    private String senderId;

    /** Current representation of the state of a conversation */
    private TrackerState tracker;

    /** Representation of the domain */
    private Domain domain;
}
