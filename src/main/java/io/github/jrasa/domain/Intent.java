package io.github.jrasa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Intent {
    private String name;
    @JsonProperty("use_entities")
    private List<String> useEntities;
    @JsonProperty("ignore_entities")
    private List<String> ignoreEntities;

    public static class IntentsDeserializer extends JsonDeserializer<List<Intent>> {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        @Override
        public List<Intent> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode root = jsonParser.getCodec().readTree(jsonParser);
            List<Intent> intents = new ArrayList<>();
            if (root.isArray()) {
                for (JsonNode intentNode : root) {
                    if (intentNode.isTextual()) {
                        String name = intentNode.asText();
                        Intent intent = new Intent();
                        intent.name = name;
                        intents.add(intent);
                    } else if (intentNode.isObject()) {
                        Iterator<String> fieldNameIterator = intentNode.fieldNames();
                        while (fieldNameIterator.hasNext()) {
                            String name = fieldNameIterator.next();
                            JsonNode intentEntities = intentNode.get(name);
                            Intent intent = OBJECT_MAPPER.treeToValue(intentEntities, Intent.class);
                            intent.name = name;
                            intents.add(intent);
                        }
                    }
                }
            }
            return intents;
        }
    }
}
