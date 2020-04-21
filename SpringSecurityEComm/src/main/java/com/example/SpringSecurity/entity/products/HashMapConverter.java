package com.example.SpringSecurity.entity.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String,String> ,String> {

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public String convertToDatabaseColumn(Map<String, String> productVariation) {

        String productVariationJson=null;
        try{
            productVariationJson=objectMapper.writeValueAsString(productVariation);
        } catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return productVariationJson;
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String productVariationJson) {
        Map<String,String> productVariation=null;
        try {
            productVariation = objectMapper.readValue(productVariationJson, Map.class);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        return productVariation;
    }
}

