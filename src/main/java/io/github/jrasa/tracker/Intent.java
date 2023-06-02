package io.github.jrasa.tracker;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Intent {
    private String name;
    private Double confidence;
}
