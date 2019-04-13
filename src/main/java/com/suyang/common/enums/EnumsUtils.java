package com.suyang.common.enums;

import java.lang.annotation.Annotation;

public class EnumsUtils {
    public static <T extends Annotation> T getAnnotation(Enum value, Class<T> clazz) throws NoSuchFieldException {
        return value.getClass().getField(value.name()).getAnnotation(clazz);
    }

    public static String getDescription(Enum value) throws NoSuchFieldException {
        return getAnnotation(value, Description.class).value();
    }
}
