package io.github.jrasa.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class BotUttered extends Event {
    public static final String NAME = "bot";

    private String text;

    @JsonProperty("metadata")
    private Map<String, Object> metaData;

    private Map<String, Object> data;

    private BotUttered() {
        super(NAME);
    }

    private BotUttered(Builder builder) {
        super(NAME, builder.timestamp);
        this.text = builder.text;
        this.metaData = builder.metaData;
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double timestamp;
        private String text;
        private Map<String, Object> metaData;
        private Map<String, Object> data;

        public BotUttered build() {
            return new BotUttered(this);
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder metaData(Map<String, Object> metaData) {
            this.metaData = metaData;
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
