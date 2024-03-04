package com.iesfranciscodelosrios.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Set<Object>, String> {

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Converts a Set of objects to a JSON string for database storage.
     *
     * @param stringObjectMap The Set of objects to convert.
     * @return The JSON string representation of the Set.
     */
    @Override
    public String convertToDatabaseColumn(Set<Object> stringObjectMap) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(stringObjectMap);
        } catch (final JsonProcessingException e) {
//            logger.error("JSON writing error", e);
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Converts a JSON string to a Set of objects when retrieving from the database.
     *
     * @param s The JSON string to convert.
     * @return The Set of objects obtained from the JSON string.
     */
    @Override
    public Set<Object> convertToEntityAttribute(String s) {
        Set<Object> result = null;
        try {
            result = objectMapper.readValue(s,
                    new TypeReference<Set<Object>>() {});
        } catch (final IOException e) {
//            logger.error("JSON reading error", e);
            e.printStackTrace();
        }

        return result;
    }
}
