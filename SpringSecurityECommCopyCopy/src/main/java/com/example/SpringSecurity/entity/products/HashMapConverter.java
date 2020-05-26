package com.example.SpringSecurity.entity.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String,String> ,String> {

    private static Logger logger = LoggerFactory.getLogger(HashMapConverter.class);

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public String convertToDatabaseColumn(Map<String, String> productVariation) {

        String productVariationJson=null;
        try{
            productVariationJson=objectMapper.writeValueAsString(productVariation);
        } catch (final JsonProcessingException e) {
            logger.error("Error Thrown: " + e);
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
            logger.error("Error Thrown: " + e);
        }
        return productVariation;
    }
}

