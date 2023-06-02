package io.github.jrasa.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Entity {
    private String entity;
    private String value;
    @JsonProperty("confidence_entity")
    private Double confidenceEntity;
    private Integer start;
    private Integer end;
    private String extractor;
    private List<String> processors;
    private String role;
    private String group;
}
