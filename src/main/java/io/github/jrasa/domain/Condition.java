package io.github.jrasa.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Condition {
    @JsonProperty("active_loop")
    private String activeLoop;
    @JsonProperty("requested_slot")
    private String requestedSlot;
}
