package com.github.julyss2019.nemolibrary.bukkit.common.util;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

import java.lang.reflect.Method;

public class ReflectUtils {
    public static Method getDeclaredMethod(@NotNull Class<?> clazz, @NotNull String methodName, @NotNull Class<?>... parameters) {
        Validator.checkNotNull(clazz, "clazz cannot be null");
        Validator.checkNotNull(methodName, "methodName cannot be null");
        Validator.checkNotNull(parameters, "parameters cannot be null");

        try {
            return clazz.getDeclaredMethod(methodName, parameters);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeMethod(@NotNull Object obj, @NotNull String methodName, @NotNull Object... parameters) {
        Validator.checkNotNull(obj, "obj cannot be null");
        Validator.checkNotNull(methodName, "methodName cannot be null");
        Validator.checkNotNull(parameters, "parameters cannot be null");

        try {
            Class<?>[] parameterTypes = new Class[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }

            return getDeclaredMethod(obj.getClass(), methodName, parameterTypes).invoke(obj);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(@NotNull Class<T> clazz) {
        Validator.checkNotNull(clazz, "clazz cannot be null");

        try {
            return clazz.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getDeclaredFieldValue(@NotNull Object obj, @NotNull String fieldName) {
        Validator.checkNotNull(obj, "obj cannot be null");
        Validator.checkNotNull(fieldName, "fieldName cannot be null");

        try {
            return obj.getClass().getDeclaredField(fieldName).get(obj);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
