package io.github.jrasa.event;

public class StoryExported extends Event {
    public static final String NAME = "export";

    public StoryExported() {
        super(NAME);
    }

    public StoryExported(Double timestamp) {
        super(NAME, timestamp);
    }
}
