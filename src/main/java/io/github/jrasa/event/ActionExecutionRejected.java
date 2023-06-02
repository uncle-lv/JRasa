package io.github.jrasa.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ActionExecutionRejected extends Event {
    public static final String NAME = "action_execution_rejected";

    @JsonProperty("name")
    private String actionName;
    private String policy;
    private Double confidence;

    private ActionExecutionRejected() {
        super(NAME);
    }

    private ActionExecutionRejected(Builder builder) {
        super(NAME, builder.timestamp);
        this.actionName = builder.actionName;
        this.policy = builder.policy;
        this.confidence = builder.confidence;
    }

    public static Builder builder(String actionName) {
        return new Builder(actionName);
    }

    public static class Builder {
        private Double timestamp;
        private final String actionName;
        private String policy;
        private Double confidence;

        public ActionExecutionRejected build() {
            return new ActionExecutionRejected(this);
        }

        public Builder policy(String policy) {
            this.policy = policy;
            return this;
        }

        public Builder confidence(Double confidence) {
            this.confidence = confidence;
            return this;
        }

        public Builder timestamp(Double timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        private Builder(String actionName) {
            this.actionName = actionName;
        }
    }
}
