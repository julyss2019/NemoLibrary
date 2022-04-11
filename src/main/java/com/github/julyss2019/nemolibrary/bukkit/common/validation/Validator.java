package com.github.julyss2019.nemolibrary.bukkit.common.validation;


import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;

public class Validator {
    public static void checkState(boolean b, @Nullable String msg) {
        if (!b) {
            if (msg != null) {
                throw new RuntimeException(msg);
            }

            throw new RuntimeException();
        }
    }

    public static void checkNotNull(Object obj, @Nullable String msg) {
        if (obj == null) {
            if (msg == null) {
                throw new NullPointerException();
            }

            throw new NullPointerException(msg);
        }
    }
}
