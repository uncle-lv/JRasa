package io.github.jrasa.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class LoopInterrupted extends Event {
    public static final String NAME = "loop_interrupted";

    @JsonProperty("is_interrupted")
    private Boolean interrupted;

    private LoopInterrupted() {
        super(NAME);
    }

    public LoopInterrupted(Boolean interrupted) {
        super(NAME);
        this.interrupted = interrupted;
    }

    public LoopInterrupted(Boolean interrupted, Double timestamp) {
        super(NAME, timestamp);
        this.interrupted = interrupted;
    }

    @JsonIgnore
    public boolean isInterrupted() {
        if (null == this.interrupted) {
            return false;
        }
        return interrupted;
    }
}
