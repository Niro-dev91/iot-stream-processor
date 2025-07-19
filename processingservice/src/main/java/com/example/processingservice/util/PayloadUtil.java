package com.example.processingservice.util;

import java.util.Map;

/*
 * Utility class for safely extracting numeric values from a generic map
 * payload.
 */

public class PayloadUtil {
    /**
     * Retrieves a Double value from the map by the given key.
     * If the value is a Number, it converts it to Double.
     * If the value is a String or other, attempts to parse it as Double.
     * Returns null if the value is missing or cannot be parsed.
     **/
    public static Double getDouble(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) {
            return ((Number) val).doubleValue();
        }
        try {
            return Double.parseDouble(val.toString());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves an Integer value from the map by the given key.
     * If the value is a Number, it converts it to Integer.
     * If the value is a String or other, attempts to parse it as Integer.
     * Returns null if the value is missing or cannot be parsed.
     **/
    public static Integer getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        try {
            return Integer.parseInt(val.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
