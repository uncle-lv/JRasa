package io.github.jrasa.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.jrasa.event.Event;
import io.github.jrasa.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class ActionResponse {
    private List<? extends Event> events;

    @JsonProperty("responses")
    private List<Message> messages;
}
