package com.github.suyang.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static <T> T parse(String json, JavaType javaType) throws IOException {
        return mapper.readValue(json, javaType);
    }

    public static String toString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static JsonNode parse(String json) throws IOException {
        return mapper.readTree(json);
    }
}
