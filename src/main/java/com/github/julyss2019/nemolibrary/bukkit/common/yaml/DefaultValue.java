package com.github.julyss2019.nemolibrary.bukkit.common.yaml;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;

public class DefaultValue<T> {
    private final T value;

    private DefaultValue(@Nullable T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public static <T> DefaultValue<T> of(@Nullable T value) {
        return new DefaultValue<T>(value);
    }
}
