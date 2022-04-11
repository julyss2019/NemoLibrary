package com.github.julyss2019.nemolibrary.bukkit.common.text;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public class Placeholder {
    private final String key;
    private final String value;

    public Placeholder(@NotNull String key, @NotNull String value) {
        Validator.checkNotNull(key, "key cannot be null");
        Validator.checkNotNull(value, "value cannot be null");

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
