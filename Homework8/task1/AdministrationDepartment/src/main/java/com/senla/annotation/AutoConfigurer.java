package com.senla.annotation;


import java.io.BufferedReader;
import java.io.FileReader;

public class AutoConfigurer {

    String DEFAULT_CONFIG_FILE = "config.properties";


    public void configure(Object target) {
        Class<?> clazz = target.getClass();

        for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ConfigProperty.class)) {
                ConfigProperty ann = field.getAnnotation(ConfigProperty.class);
                String fileName = ann.configFileName();
                String propertyName = ann.propertyName();
                Class<?> classType = ann.type();
                if(fileName.isEmpty()){ //  если не указано имя файла/пустой файл, то берем наш файл
                    fileName = DEFAULT_CONFIG_FILE;
                }
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


                try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
                    String ln;
                    while ((ln = br.readLine()) != null){
                        String[] line = ln.trim().split("\\s*=\\s*", 2);
                        String key = line[0];
                        String value = line[1].trim();
                        if(key.equals(propertyName)){
                            if(classType == Integer.class || classType == int.class){
                                Integer valueInteger = Integer.parseInt(value);
                                field.set(target, valueInteger);
                            }
                            else if(classType == Boolean.class || classType == boolean.class){
                                Boolean valueBoolean = Boolean.parseBoolean(value);
                                field.set(target, valueBoolean);
                            }
                            else if (classType == long.class || classType == Long.class) {
                                Long valueLong = Long.parseLong(value);
                                field.set(target, valueLong);
                            }
                            else if(classType == double.class || classType == Double.class){
                                Double valueDouble = Double.parseDouble(value);
                                field.set(target, valueDouble);
                            }
                            else if(classType == String.class){
                                field.set(target, value);
                            }

                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
