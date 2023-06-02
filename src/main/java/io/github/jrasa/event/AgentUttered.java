package io.github.jrasa.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class AgentUttered extends Event {
    public static final String NAME = "agent";

    private String text;

    private Map<String, Object> data;

    private AgentUttered() {
        super(NAME);
    }

    private AgentUttered(Builder builder) {
        super(NAME, builder.timestamp);
        this.text = builder.text;
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double timestamp;
        private String text;
        private Map<String, Object> data;

        public AgentUttered build() {
            return new AgentUttered(this);
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public Builder timestamp(Double timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        private Builder() {}
    }
}
