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
                if(fileName.isEmpty()){
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


                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
