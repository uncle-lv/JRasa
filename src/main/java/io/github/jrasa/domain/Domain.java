package io.github.jrasa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * A representation of the domain.
 *
 * @author uncle-lv
 */
@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Domain {
    @JsonDeserialize(using = Intent.IntentsDeserializer.class)
    private List<Intent> intents;
    private List<String> entities;
    private Map<String, Slot> slots;
    private Map<String, List<Response>> responses;
    private List<String> actions;
    private Map<String, Form> forms;
}
