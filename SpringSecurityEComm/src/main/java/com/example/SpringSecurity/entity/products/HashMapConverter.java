//package com.example.SpringSecurity.entity.products;
//
//import org.codehaus.jackson.JsonParseException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//import java.io.IOException;
//import java.util.Map;
//
//@Converter
//public class HashMapConverter implements AttributeConverter<Map<String, String>, String> {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Override
//    public String convertToDatabaseColumn(Map<String, String> productInfo) {
//        String productInfoJson = null;
//        try {
//            productInfoJson = objectMapper.writeValueAsString(productInfo);
//        } catch (final IOException e) {
//            e.printStackTrace();
//        }
//        return productInfoJson;
//    }
//
//    @Override
//    public Map<String, String> convertToEntityAttribute(String productInfJSON) {
//        Map<String, String> productInfo = null;
//        try {
//            productInfo = objectMapper.readValue(productInfJSON, Map.class);
//        } catch (JsonParseException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return productInfo;
//    }
//}
