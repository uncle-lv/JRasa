package io.github.jrasa.message;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A representation of the response.
 *
 * @author uncle-lv
 */
@Getter
@Builder
@EqualsAndHashCode
@ToString
@JsonSerialize(using = Message.Serializer.class)
public class Message {
    private String text;
    private String image;
    private Map<String, Object> jsonMessage;
    private String response;
    private String attachment;
    private List<Button> buttons;
    private List<Map<String, Object>> elements;

    private Map<String, Object> kwargs;

    public static class Serializer extends JsonSerializer<Message> {

        @Override
        public void serialize(Message message, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("text", message.text);
            jsonGenerator.writeStringField("image", message.image);
            if (null == message.jsonMessage || message.jsonMessage.isEmpty()) {
                jsonGenerator.writeFieldName("custom");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeEndObject();
            } else {
                jsonGenerator.writeObjectField("custom", message.jsonMessage);
            }
            jsonGenerator.writeStringField("response", message.response);
            jsonGenerator.writeStringField("attachment", message.attachment);
            if (null == message.buttons || message.buttons.isEmpty()) {
                jsonGenerator.writeFieldName("buttons");
                jsonGenerator.writeStartArray();
                jsonGenerator.writeEndArray();
            } else {
                jsonGenerator.writeObjectField("buttons", message.buttons);
            }
            if (null == message.elements || message.elements.isEmpty()) {
                jsonGenerator.writeFieldName("elements");
                jsonGenerator.writeStartArray();
                jsonGenerator.writeEndArray();
            } else {
                jsonGenerator.writeObjectField("elements", message.elements);
            }
            if (message.kwargs != null) {
                for (Map.Entry<String, Object> entry : message.kwargs.entrySet()) {
                    jsonGenerator.writeObjectField(entry.getKey(), entry.getValue());
                }
            }
            jsonGenerator.writeEndObject();
        }
    }
}
