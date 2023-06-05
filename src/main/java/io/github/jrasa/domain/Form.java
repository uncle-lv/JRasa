package io.github.jrasa.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Form {
    @JsonProperty("required_slots")
    private List<String> requiredSlots;
    @JsonProperty("ignored_intents")
    private List<String> ignoredIntents;
}
