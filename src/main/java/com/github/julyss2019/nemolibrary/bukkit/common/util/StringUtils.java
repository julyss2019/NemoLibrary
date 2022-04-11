package com.github.julyss2019.nemolibrary.bukkit.common.util;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;

public class StringUtils {
    public static boolean isEmpty(@Nullable String str) {
        return str != null && str.isEmpty();
    }

    public static boolean isDouble(@Nullable String str) {
        Validator.checkNotNull(str, "str cannot be null");

        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInteger(@NotNull String str) {
        Validator.checkNotNull(str, "str cannot be null");

        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
