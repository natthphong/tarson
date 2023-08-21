package com.tar.tarson.utils;

public class NumberUtils {
    public static boolean isNumber(Object value) {
        return value instanceof Number;
    }

    public static  boolean isPositiveNumber(Object value) {
        return value instanceof Number && ((Number) value).doubleValue() > 0;
    }

    public static boolean hasCorrectSize(Object value,Integer size) {
        if (value instanceof String) {
            return ((String) value).length() <= size;
        } else if (value instanceof Iterable) {
            return ((Iterable<?>) value).spliterator().getExactSizeIfKnown() <= size;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue() <= size;
        }
        return false;
    }

}
