package io.github.jrasa.event;

public class ConversationResumed extends Event {
    public static final String NAME = "resume";

    public ConversationResumed() {
        super(NAME);
    }

    public ConversationResumed(Double timestamp) {
        super(NAME, timestamp);
    }
}
