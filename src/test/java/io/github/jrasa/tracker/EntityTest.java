package io.github.jrasa.tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityTest {
    private static final String JSON = "{" +
              "\"entity\": \"location\"," +
              "\"value\": \"New York\"," +
              "\"confidence_entity\": 0.9740062952041626," +
              "\"start\": 26," +
              "\"end\": 34," +
              "\"extractor\": \"DIETClassifier\"," +
              "\"role\": \"destination\"," +
              "\"group\": \"city\"," +
              "\"processors\": [" +
                "\"EntitySynonymMapper\"" +
              "]" +
            "}";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void testDeserialize() throws JsonProcessingException {
        Entity entity = OBJECT_MAPPER.readValue(JSON, Entity.class);
        Assertions.assertEquals("location", entity.getEntity());
        Assertions.assertEquals("New York", entity.getValue());
        Assertions.assertEquals(0.9740062952041626, entity.getConfidenceEntity());
        Assertions.assertEquals(26, entity.getStart());
        Assertions.assertEquals(34, entity.getEnd());
        Assertions.assertEquals("DIETClassifier", entity.getExtractor());
        Assertions.assertEquals("destination", entity.getRole());
        Assertions.assertEquals("city", entity.getGroup());
        Assertions.assertEquals(1, entity.getProcessors().size());
        Assertions.assertEquals("EntitySynonymMapper", entity.getProcessors().get(0));
        Assertions.assertEquals("Entity(entity=location, value=New York, confidenceEntity=0.9740062952041626, start=26, end=34, extractor=DIETClassifier, processors=[EntitySynonymMapper], role=destination, group=city)",
                entity.toString());
    }
}
