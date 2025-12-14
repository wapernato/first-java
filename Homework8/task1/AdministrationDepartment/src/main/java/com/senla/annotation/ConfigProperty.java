package com.senla.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target(ElementType.FIELD) // СТАВИМ С КАКИМ ТИПО ДАННЫХ МЫ БУДЕМ РАБОТАТЬ - FIELD ЭТО ПОЛЕ
@Retention(RetentionPolicy.RUNTIME)

public @interface ConfigProperty {
    String configFileName() default ""; // должен быть именем файла конфигурации по умолчанию
    String propertyName() default  ""; // иметь вид ИМЯ_КЛАССА.ИМЯ_ПОЛЯ
    Class<?> type() default void.class; // преобразовывать значение конфигурируемого параметра в текущий тип поля, либо в String, если тип общий
}
