package io.github.jrasa.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Mapping {
    private String type;
    private String entity;
    private String role;
    private String group;
    private String intent;
    @JsonProperty("not_intent")
    private String notIntent;
    private String value;
    private String action;
    private List<Condition> conditions;
}
