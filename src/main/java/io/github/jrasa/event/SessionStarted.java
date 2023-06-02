package io.github.jrasa.event;

public class SessionStarted extends Event {
    public static final String NAME = "session_started";

    public SessionStarted() {
        super(NAME);
    }

    public SessionStarted(Double timestamp) {
        super(NAME, timestamp);
    }
}
