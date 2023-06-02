package io.github.jrasa.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class FollowupAction extends Event {
    public static final String NAME = "followup";

    private String name;

    private FollowupAction() {
        super(NAME);
    }

    public FollowupAction(String name) {
        this.name = name;
    }

    public FollowupAction(String name, Double timestamp) {
        super(NAME, timestamp);
        this.name = name;
    }
}
