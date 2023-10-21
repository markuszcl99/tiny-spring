package com.tiny.spring.util;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;

/**
 * @author: markus
 * @date: 2023/10/21 11:32 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class FieldUtils {

    @Nullable
    public static Field getFiled(Class<?> clazz, String fieldName) {
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return getFiled(clazz.getSuperclass(), fieldName);
        }
    }
}
