package io.github.jrasa.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Slot {
    private String type;
    @JsonProperty("initial_value")
    private Object initialValue;
    @JsonProperty("influence_conversation")
    private Boolean influenceConversation;
    private List<Mapping> mappings;
}
