package io.github.jrasa.event;

public class UserUtteranceReverted extends Event {
    public static final String NAME = "rewind";

    public UserUtteranceReverted() {
        super(NAME);
    }

    public UserUtteranceReverted(Double timestamp) {
        super(NAME, timestamp);
    }
}
