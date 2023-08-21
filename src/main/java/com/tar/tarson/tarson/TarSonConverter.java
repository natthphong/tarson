package com.tar.tarson.tarson;


import com.tar.tarson.exception.TarException;
import com.tar.tarson.utils.Constant;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TarSonConverter {
    public String objectToJsonString(Object object) {
        if (object == null) {
            return "{}";
        }
        StringBuilder jsonBuilder = new StringBuilder();
        if (object instanceof List<?> || object.getClass().isArray()) {
            jsonBuilder.append("[");
            List<?> list = object instanceof List ? (List<?>) object : Arrays.asList((Object[]) object);
            for (Object item : list) {
                jsonBuilder.append(objectToJsonString(item)).append(",");
            }
            if (!list.isEmpty()) {
                jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
            }
            jsonBuilder.append("]");
        } else if (object instanceof LocalDate) {
            jsonBuilder.append("\"").append(escapeJson(((LocalDate) object).format(Constant.Date.DATE_FORMATTER))).append("\"");
        } else if (object instanceof LocalDateTime) {
            jsonBuilder.append("\"").append(escapeJson(((LocalDateTime) object).format(Constant.Date.DATETIME_FORMATTER))).append("\"");
        } else if (object instanceof String) {
            jsonBuilder.append("\"").append(escapeJson(object.toString())).append("\"");
        } else if (object instanceof Number || object instanceof Boolean) {
            jsonBuilder.append(object);
        } else {
            jsonBuilder.append("{");
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            int count = 0;
            for (Field field : fields) {
                String fieldName = field.getName();
                Object fieldValue;
                try {
                    field.setAccessible(true);
                    fieldValue = field.get(object);
                } catch (IllegalAccessException e) {
                    fieldValue = null;
                }

                if (fieldValue != null || field.getType() == boolean.class || field.getType() == Boolean.class) {
                    if (count > 0) {
                        jsonBuilder.append(",");
                    }
                    jsonBuilder.append("\"").append(fieldName).append("\":");
                    if (fieldValue != null) {
                        jsonBuilder.append(objectToJsonString(fieldValue));
                    } else {
                        jsonBuilder.append("null");
                    }
                    count++;
                }
            }
            jsonBuilder.append("}");
        }
        return jsonBuilder.toString();
    }

    private static String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\"", "\\\"");
    }


    public <T> T jsonToObject(String json, Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.newInstance();
            json = json.trim();

            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1);
                String[] elements = json.split("\\},\\{");
                for (String element : elements) {
                    element = element.trim();
                    if (!element.isEmpty()) {
                        element = "{" + element + "}";
                        populateFields(instance, element);
                    }
                }
            } else {

                populateFields(instance, json);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            throw new TarException(e.getMessage());
        }
        return instance;
    }

    public <T> List<T> jsonToListObject(String json, Class<T> elementType) {
        List<T> list = new ArrayList<>();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1);

            String[] elements = json.split("\\{");
            for (String element : elements) {
                element = element.trim();
                if (!element.isEmpty()) {
                    if (element.endsWith("},")) {
                        element = element.substring(0, element.length() - 1);
                    }
                    element = "{" + element;

                    T value = jsonToObject(element, elementType);

                    list.add(value);
                }
            }
        }
        return list;
    }


    private void populateFields(Object instance, String json) {
        try {
            Field[] fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();


                if (json.contains("\"" + fieldName + "\":") || json.contains(fieldName + ":")) {
                    int start = 0;
                    if (json.contains("\"" + fieldName + "\":")) {
                        start = json.indexOf("\"" + fieldName + "\":") + fieldName.length() + 3;
                    } else {
                        start = json.indexOf(fieldName + ":") + fieldName.length() + 1;
                    }
                    int end = json.indexOf(",", start);
                    if (end == -1) {
                        end = json.indexOf("}", start);
                    }
                    if (json.substring(start).startsWith("[")) {
                        end = json.indexOf("]", start) + 1;
                    }
                    String fieldValue = json.substring(start, end).trim();
                    fieldValue = fieldValue.replaceAll("\"", "");
                    field.setAccessible(true);
                    if (field.getType() == String.class || field.getType() == char.class) {
                        field.set(instance, fieldValue);
                    } else if (field.getType() == int.class || field.getType() == Integer.class) {
                        if (!fieldValue.isEmpty()) {
                            field.set(instance, Integer.parseInt(fieldValue));
                        }
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        if (!fieldValue.isEmpty()) {
                            field.set(instance, Long.parseLong(fieldValue));
                        }
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        if (!fieldValue.isEmpty()) {
                            field.set(instance, Double.parseDouble(fieldValue));
                        }
                    } else if (field.getType() == float.class || field.getType() == Float.class) {
                        if (!fieldValue.isEmpty()) {
                            field.set(instance, Float.parseFloat(fieldValue));
                        }
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        if (!fieldValue.isEmpty()) {
                            field.set(instance, Boolean.parseBoolean(fieldValue));
                        }
                    } else if (field.getType() == LocalDate.class) {
                        if (!fieldValue.isEmpty()) {
                            LocalDate localDate = LocalDate.parse(fieldValue, Constant.Date.DATE_FORMATTER);
                            field.set(instance, localDate);
                        }
                    } else if (field.getType() == LocalDateTime.class) {
                        if (!fieldValue.isEmpty()) {
                            LocalDateTime localDateTime = LocalDateTime.parse(fieldValue, Constant.Date.DATETIME_FORMATTER);
                            field.set(instance, localDateTime);
                        }
                    } else if (field.getType().isAssignableFrom(List.class) || field.getType().isArray()) {
                        if (fieldValue.startsWith("[") && fieldValue.endsWith("]")) {
                            Type fieldType = field.getGenericType();
                            if (fieldType instanceof ParameterizedType) {
                                ParameterizedType parameterizedType = (ParameterizedType) fieldType;
                                Type[] typeArgs = parameterizedType.getActualTypeArguments();
                                if (typeArgs.length == 1 && typeArgs[0] instanceof Class) {
                                    Class<?> listElementType = (Class<?>) typeArgs[0];
                                    List<?> listValue = jsonToListObject(fieldValue, listElementType);
                                    if (field.getType().isArray()) {
                                        field.set(instance, listValue.toArray());
                                    } else {
                                        field.set(instance, listValue);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new TarException(e.getMessage());
        }
    }


}
