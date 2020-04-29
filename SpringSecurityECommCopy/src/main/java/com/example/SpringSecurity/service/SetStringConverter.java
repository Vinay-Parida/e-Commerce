package com.example.SpringSecurity.service;

import java.util.HashSet;
import java.util.Set;

public class SetStringConverter {
    private static final String delimiter = ",";

    public static String convertToString(Set<String> stringSet){
        String stringValue = "";
        if (stringSet.isEmpty())
            return stringValue;
        else {
            stringValue = String.join(delimiter, stringSet);
            return stringValue;
        }
    }

    public static Set<String> convertToSet(String string){
        Set<String> stringSet = new HashSet<>();
        String[] values = string.split(delimiter);
        for (String value: values) {
            stringSet.add(value);
        }
        return stringSet;
    }

}
