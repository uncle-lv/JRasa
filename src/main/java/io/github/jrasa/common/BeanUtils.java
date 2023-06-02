package io.github.jrasa.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T deepCopy(T t) {
        if (null == t) {
            return null;
        }

        T copy;
        try {
            copy = OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(t), (Class<T>) t.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    private BeanUtils() {
        // avoid invoking constructor within class
        throw new AssertionError();
    }
}
