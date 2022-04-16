package com.github.julyss2019.nemolibrary.bukkit.common.text;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

import java.util.*;

public class PlaceholderContainer implements PlaceholderHandler {
    private final Map<String, Object> map = new HashMap<>();

    public PlaceholderContainer put(@NotNull String key, @NotNull Object value) {
        Validator.checkNotNull(key, "key cannot be null");
        Validator.checkNotNull(value, "value cannot be null");

        map.put(key, value);
        return this;
    }

    @Override
    public Object getValue(@NotNull String key) {
        return map.get(key);
    }
}
