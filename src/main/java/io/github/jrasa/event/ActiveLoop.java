package io.github.jrasa.event;

public class ActiveLoop extends Event {
    public static final String NAME = "active_loop";

    public ActiveLoop() {
        super(NAME);
    }

    public ActiveLoop(Double timestamp) {
        super(NAME, timestamp);
    }
}