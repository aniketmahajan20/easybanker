// Doesn't work right now
package com.example.demo.easybanker.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class RolesConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> roles) {
        try {
            return objectMapper.writeValueAsString(roles); // Convert list to JSON
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {}); // Convert JSON to list
        } catch (Exception e) {
            throw new IllegalArgumentException("Error converting JSON to list", e);
        }
    }

    
}
