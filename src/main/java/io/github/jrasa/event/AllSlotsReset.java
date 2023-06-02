package io.github.jrasa.event;

public class AllSlotsReset extends Event {
    public static final String NAME = "reset_slots";

    public AllSlotsReset() {
        super(NAME);
    }

    public AllSlotsReset(Double timestamp) {
        super(NAME, timestamp);
    }
}
