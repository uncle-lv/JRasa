package io.github.jrasa.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserUttered extends Event {
    public static final String NAME = "user";

    private String text;

    @JsonProperty("input_channel")
    private String inputChannel;

    @JsonProperty("parse_data")
    private Map<String, Object> parseData;

    @JsonProperty("metadata")
    private Map<String, Object> metaData;

    @JsonProperty("message_id")
    private String messageId;

    private UserUttered() {
        super(NAME);
    }

    private UserUttered(Builder builder) {
        super(NAME, builder.timestamp);
        this.text = builder.text;
        this.inputChannel = builder.inputChannel;
        this.parseData = builder.parseData;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double timestamp;
        private String text;
        private String inputChannel;
        private Map<String, Object> parseData;

        public UserUttered build() {
            return new UserUttered(this);
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder inputChannel(String inputChannel) {
            this.inputChannel = inputChannel;
            return this;
        }

        public Builder parseData(Map<String, Object> parseData) {
            this.parseData = parseData;
            return this;
        }

        public Builder timestamp(Double timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        private Builder() {}
    }
}
