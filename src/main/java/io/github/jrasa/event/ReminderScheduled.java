package io.github.jrasa.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.jrasa.common.ActionNameChecker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = ReminderScheduled.Deserializer.class)
public class ReminderScheduled extends Event {
    public static final String NAME = "reminder";

    @JsonProperty("intent")
    private String intentName;

    @JsonProperty("date_time")
    private LocalDateTime triggerDateTime;

    private List<Entity> entities;

    private String name;

    @JsonProperty("kill_on_user_msg")
    private Boolean killOnUserMessage;

    private ReminderScheduled() {
        super(NAME);
    }

    private ReminderScheduled(Builder builder) {
        super(NAME, builder.timestamp);
        this.intentName = builder.intentName;
        this.triggerDateTime = builder.triggerDateTime;
        this.entities = builder.entities;
        this.name = builder.name;
        this.killOnUserMessage = builder.killOnUserMessage;
    }

    public static Builder builder(String intentName) {
        return new Builder(intentName);
    }

    public static class Builder {
        private Double timestamp;
        private final String intentName;
        private LocalDateTime triggerDateTime;
        private List<Entity> entities;
        private String name;
        private Boolean killOnUserMessage = true;

        public ReminderScheduled build() {
            return new ReminderScheduled(this);
        }

        public Builder triggerDateTime(LocalDateTime triggerDateTime) {
            this.triggerDateTime = triggerDateTime;
            return this;
        }

        public Builder entities(List<Entity> entities) {
            this.entities = entities;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder killOnUserMessage(Boolean killOnUserMessage) {
            this.killOnUserMessage = killOnUserMessage;
            return this;
        }

        public Builder timestamp(Double timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        private Builder(String intentName) {
            if (ActionNameChecker.isProbablyActionName(intentName)) {
                log.warn(getWarnInfo(intentName));
            }
            this.intentName = intentName;
        }

        private static String getWarnInfo(String intentName) {
            return "ReminderScheduled intent starts with 'utter_' or 'action_'. " +
                    "If '" + intentName + "' is indeed an intent, " +
                    "then you can ignore this warning.";
        }
    }

    public static class Deserializer extends JsonDeserializer<ReminderScheduled> {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        @Override
        public ReminderScheduled deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode root = jsonParser.getCodec().readTree(jsonParser);

            JsonNode timestampNode = root.get("timestamp");
            Double timestamp = null;
            if (timestampNode != null) {
                timestamp = timestampNode.asDouble();
            }

            JsonNode intentNode = root.get("intent");
            String intentName = null;
            if (intentNode != null) {
                intentName = intentNode.asText();
            }

            JsonNode dateTimeNode =  root.get("date_time");
            LocalDateTime triggerDateTime = null;
            if (dateTimeNode != null) {
                triggerDateTime = LocalDateTime.parse(dateTimeNode.asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }

            JsonNode entitiesNode = root.get("entities");
            List<Entity> entities = new ArrayList<>();
            if (null != entitiesNode) {
                if (entitiesNode.isArray()) {
                    for (JsonNode node : entitiesNode) {
                        if (node.isObject()) {
                            Entity entity = OBJECT_MAPPER.treeToValue(node, Entity.class);
                            entities.add(entity);
                        }
                    }
                } else if (entitiesNode.isObject()) {
                    Entity entity = OBJECT_MAPPER.treeToValue(entitiesNode, Entity.class);
                    entities.add(entity);
                }
            }

            JsonNode nameNode = root.get("name");
            String name = null;
            if (nameNode != null) {
                name = nameNode.asText();
            }

            JsonNode killOnUserMsgNode = root.get("kill_on_user_msg");
            Boolean killOnUserMessage = null;
            if (killOnUserMsgNode != null) {
                killOnUserMessage = killOnUserMsgNode.asBoolean();
            }

            return ReminderScheduled.builder(intentName)
                    .timestamp(timestamp)
                    .triggerDateTime(triggerDateTime)
                    .entities(entities)
                    .name(name)
                    .killOnUserMessage(killOnUserMessage)
                    .build();
        }
    }
}
