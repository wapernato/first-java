package com.senla.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // СТАВИМ С КАКИМ ТИПО ДАННЫХ МЫ БУДЕМ РАБОТАТЬ - FIELD ЭТО ПОЛЕ
@Retention(RetentionPolicy.RUNTIME)

public @interface Inject {}
