package io.github.jrasa.event;

public class Restarted extends Event {
    public static final String NAME = "restart";

    public Restarted() {
        super(NAME);
    }

    public Restarted(Double timestamp) {
        super(NAME, timestamp);
    }
}
