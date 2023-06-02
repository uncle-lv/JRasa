package io.github.jrasa.event;

public class ConversationPaused extends Event {
    public static final String NAME = "pause";

    public ConversationPaused() {
        super(NAME);
    }

    public ConversationPaused(Double timestamp) {
        super(NAME, timestamp);
    }
}
