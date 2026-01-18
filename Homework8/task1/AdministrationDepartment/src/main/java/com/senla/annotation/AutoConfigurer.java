package com.senla.annotation;


import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class AutoConfigurer {

    private final Map<String, Map<String, String>> fileCache = new HashMap<>();
    private static final String DEFAULT_CONFIG_FILE = "C:\\Users\\wapernato\\CoursesHomework\\Homework8\\task1\\AdministrationDepartment\\src\\main\\java\\com\\senla\\resources\\config.properties";

    public void configure(Object target) throws IllegalAccessException {
        Class<?> clazz = target.getClass();


        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if(Modifier.isFinal(field.getModifiers())) continue;
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                ConfigProperty ann = field.getAnnotation(ConfigProperty.class);
                String fileName = ann.configFileName();
                String propertyName = ann.propertyName();
                Class<?> classType = ann.type();

                if (fileName == null || fileName.isEmpty()) {
                    fileName = DEFAULT_CONFIG_FILE;
                }
                Map<String, String> propertyKeyValue = fileCache.computeIfAbsent(fileName, this::readFile);

                if(propertyName.isEmpty()){
                    String simpleName = clazz.getSimpleName();
                    propertyName = simpleName + "." + field.getName();
                }
                if(classType == void.class){
                    classType = field.getType();
                }
                if(classType == Object.class){
                    classType = String.class;

                }

                if(propertyKeyValue.containsKey(propertyName)){
                    if(classType == int[].class){
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");
                        int length = parts.length;
                        int[] valueArray = new int[length];
                        for(int i = 0; i < length ; i++){
                            valueArray[i] = Integer.parseInt(parts[i]);
                        }
                        field.set(target, valueArray);
                    }

                    else if(classType == Integer[].class){
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");
                        int length = parts.length;
                        Integer[] valueArray = new Integer[length];
                        for(int i = 0; i < length ; i++){
                            valueArray[i] = Integer.parseInt(parts[i]);
                        }
                        field.set(target, valueArray);
                    }

                    else if(classType == boolean[].class){
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");
                        int length = parts.length;
                        boolean[] valueArray = new boolean[length];
                        for(int i = 0; i < length ; i++){
                            valueArray[i] = Boolean.parseBoolean(parts[i]);
                        }
                        field.set(target, valueArray);
                    }
                    else if(classType == Boolean[].class){
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");
                        int length = parts.length;
                        Boolean[] valueArray = new Boolean[length];
                        for(int i = 0; i < length ; i++){
                            valueArray[i] = Boolean.parseBoolean(parts[i]);
                        }
                        field.set(target, valueArray);
                    }

                    else if(classType == String[].class){
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");
                        int length = parts.length;
                        String[] valueArray = new String[length];
                        System.arraycopy(parts, 0, valueArray, 0, length);
                        field.set(target, valueArray);
                    }

                    else if(classType == long[].class){
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");
                        int length = parts.length;
                        long[] valueArray = new long[length];
                        for(int i = 0; i < length ; i++){
                            valueArray[i] = Long.parseLong(parts[i]);
                        }
                        field.set(target, valueArray);
                    }

                    else if(classType == Long[].class){
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");
                        int length = parts.length;
                        Long[] valueArray = new Long[length];
                        for(int i = 0; i < length ; i++){
                            valueArray[i] = Long.parseLong(parts[i]);
                        }
                        field.set(target, valueArray);
                    }

                    else if(List.class.isAssignableFrom(field.getType())){
                        Class<?> elemType = String.class;
                        if (ann.type() != void.class && ann.type() != Object.class) {
                            elemType = ann.type();
                    } else {
                         Type gt = field.getGenericType();
                         if(gt instanceof ParameterizedType pt){
                             Type arg = pt.getActualTypeArguments()[0];
                             if (arg instanceof Class<?> c) elemType = c;
                         }
                    }

                        Class<?> listType = field.getType();
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");

                        List<Object> valueList;

                        if(LinkedList.class.isAssignableFrom(listType)){
                            valueList = new LinkedList<>();
                        }
                        else {
                            valueList = new ArrayList<>();
                        }

                        for (String part : parts) {
                            valueList.add(parseOne(part, elemType));
                        }
                        field.set(target, valueList);
                    }


                    else if(Set.class.isAssignableFrom(field.getType())) {

                        Class<?> elemType = String.class;
                        if (ann.type() != void.class && ann.type() != Object.class) {
                            elemType = ann.type();
                        } else {
                            Type gt = field.getGenericType();
                            if (gt instanceof ParameterizedType pt) {
                                Type arg = pt.getActualTypeArguments()[0];
                                if (arg instanceof Class<?> c) elemType = c;
                            }
                        }

                        Class<?> setType = field.getType();
                        String[] parts = propertyKeyValue.get(propertyName).trim().split("\\s*,\\s*");

                        Set<Object> valueSet;

                        if (SortedSet.class.isAssignableFrom(setType) || NavigableSet.class.isAssignableFrom(setType)) {
                            valueSet = new TreeSet<>();
                        }
                        else if (LinkedHashSet.class.isAssignableFrom(setType)) {
                            valueSet = new LinkedHashSet<>();
                        }
                        else{
                            valueSet = new HashSet<>();
                        }

                        for (String part : parts) {
                            valueSet.add(parseOne(part, elemType));
                        }
                        field.set(target, valueSet);
                    }

                    else if(classType == Integer.class || classType == int.class){
                        String parts = propertyKeyValue.get(propertyName).trim();
                        Integer valueInteger = Integer.parseInt(parts);
                        field.set(target, valueInteger);
                    }
                    else if(classType == Boolean.class || classType == boolean.class){
                        String parts = propertyKeyValue.get(propertyName).trim();
                        Boolean valueBoolean = Boolean.parseBoolean(parts);
                        field.set(target, valueBoolean);
                    }
                    else if (classType == long.class || classType == Long.class) {
                        String parts = propertyKeyValue.get(propertyName).trim();
                        Long valueLong = Long.parseLong(parts);
                        field.set(target, valueLong);
                    }
                    else if(classType == double.class || classType == Double.class){
                        String parts = propertyKeyValue.get(propertyName).trim();
                        Double valueDouble = Double.parseDouble(parts);
                        field.set(target, valueDouble);
                    }
                    else if(classType == String.class){
                        String parts = propertyKeyValue.get(propertyName).trim();
                        field.set(target, parts);
                    }
                    else {
                        String raw = propertyKeyValue.get(propertyName).trim();
                        field.set(target, parseOne(raw, classType));
                    }
                }
            }
        }
    }


    private Map<String, String> readFile(String fileName){
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String ln;
            while ((ln = br.readLine()) != null){
                ln = ln.trim();
                if(ln.isEmpty()) continue;
                String[] line = ln.split("\\s*=\\s*", 2);
                String key = line[0];
                String value = line[1];
                map.put(key.trim(), value.trim());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    private Object parseOne(String s, Class<?> t) {
        s = s.trim();
        if (t.isEnum()) {
            @SuppressWarnings({"rawtypes", "unchecked"})
            Class<? extends Enum> et = (Class<? extends Enum>) t;
            return Enum.valueOf((Class) et, s);
        }
        if (t == String.class) return s;
        if (t == Integer.class || t == int.class) return Integer.parseInt(s);
        if (t == Long.class || t == long.class) return Long.parseLong(s);
        if (t == Boolean.class || t == boolean.class) return Boolean.parseBoolean(s);
        if (t == Double.class || t == double.class) return Double.parseDouble(s);
        throw new IllegalArgumentException("Unsupported type: " + t.getName());
    }
}
