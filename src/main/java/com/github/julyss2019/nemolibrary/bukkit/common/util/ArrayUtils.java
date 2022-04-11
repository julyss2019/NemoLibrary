package com.github.julyss2019.nemolibrary.bukkit.common.util;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

import java.util.Objects;

public class ArrayUtils {
    public static boolean containsElement(@NotNull Object[] array, Object obj) {
        Validator.checkNotNull(array, "array cannot be null");

        for (Object o : array) {
            if (Objects.equals(obj, o)) {
                return true;
            }
        }

        return false;
    }
}
