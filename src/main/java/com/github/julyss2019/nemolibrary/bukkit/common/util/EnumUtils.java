package com.github.julyss2019.nemolibrary.bukkit.common.util;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EnumUtils {
    public static <T> T getEnum(@NotNull Class<T> clazz, @NotNull String name) {
        Validator.checkNotNull(clazz, "clazz cannot be null");
        Validator.checkNotNull(name, "name cannot be null");

        for (Object enumConstant : clazz.getEnumConstants()) {
            Method nameMethod = null;

            try {
                nameMethod = clazz.getMethod("name");

                nameMethod.setAccessible(true);

                if (nameMethod.invoke(enumConstant).equals(name)) {
                    return (T) enumConstant;
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            } finally {
                if (nameMethod != null) {
                    nameMethod.setAccessible(false);
                }
            }
        }

        return null;
    }
}
