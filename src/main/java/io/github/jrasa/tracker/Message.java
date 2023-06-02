package io.github.jrasa.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class Message {

    private Intent intent;

    private List<Entity> entities;

    private String text;

    @JsonProperty("intent_ranking")
    private List<Intent> intentRanking;
}
