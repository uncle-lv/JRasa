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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(using = ReminderCancelled.Deserializer.class)
public class ReminderCancelled extends Event {
    public static final String NAME = "cancel_reminder";

    private String name;

    @JsonProperty("intent")
    private String intentName;

    private List<Entity> entities;

    private ReminderCancelled() {
        super(NAME);
    }

    private ReminderCancelled(Builder builder) {
        super(NAME, builder.timestamp);
        this.name = builder.name;
        this.intentName = builder.intentName;
        this.entities = builder.entities;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Double timestamp;
        private String name;
        private String intentName;
        private List<Entity> entities;

        public ReminderCancelled build() {
            return new ReminderCancelled(this);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder intentName(String intentName) {
            if (ActionNameChecker.isProbablyActionName(intentName)) {
                log.warn(getWarnInfo(intentName));
            }
            this.intentName = intentName;
            return this;
        }

        public Builder entities(List<Entity> entities) {
            this.entities = entities;
            return this;
        }

        public Builder timestamp(Double timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        private static String getWarnInfo(String intentName) {
            return "ReminderCancelled intent starts with 'utter_' or 'action_'. " +
                    "If '" + intentName + "' is indeed an intent, " +
                    "then you can ignore this warning.";
        }

        private Builder() {}
    }

    public static class Deserializer extends JsonDeserializer<ReminderCancelled> {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        @Override
        public ReminderCancelled deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
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

            return ReminderCancelled.builder()
                    .intentName(intentName)
                    .timestamp(timestamp)
                    .entities(entities)
                    .name(name)
                    .build();
        }
    }
}
