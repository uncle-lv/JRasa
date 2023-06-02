package io.github.jrasa.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class SlotSet extends Event {
    public static final String NAME = "slot";

    private String name;

    private Object value;

    private SlotSet() {
        super(NAME);
    }

    public SlotSet(String name, Object value) {
        super(NAME);
        this.name = name;
        this.value = value;
    }

    public SlotSet(String name, Object value, Double timestamp) {
        super(NAME, timestamp);
        this.name = name;
        this.value = value;
    }
}
