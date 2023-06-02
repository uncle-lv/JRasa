package io.github.jrasa.event;

public class ActionReverted extends Event {
    public static final String NAME = "undo";

    public ActionReverted() {
        super(NAME);
    }

    public ActionReverted(Double timestamp) {
        super(NAME, timestamp);
    }
}
