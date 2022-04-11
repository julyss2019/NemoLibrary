package com.github.julyss2019.nemolibrary.bukkit.common.locale.resource;

import com.github.julyss2019.nemolibrary.bukkit.common.text.PlaceholderHandler;
import com.github.julyss2019.nemolibrary.bukkit.common.text.Texts;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;

import java.util.List;

public interface LocaleResource extends PlaceholderHandler {
    LocaleResource getNode(@NotNull String key);

    List<String> getStringList(@NotNull String key);

    default List<String> getStringList(@NotNull String key, PlaceholderHandler placeholderHandler) {
        Validator.checkNotNull(key, "key cannot be null");

        List<String> value = getStringList(key);

        if (placeholderHandler == null) {
            return value;
        }

        return Texts.setPlaceholders(value, placeholderHandler);
    }

    String getString(@NotNull String key);

    default String getString(@NotNull String key, @Nullable PlaceholderHandler placeholderHandler) {
        Validator.checkNotNull(key, "key cannot be null");

        String value = getString(key);

        if (placeholderHandler == null) {
            return value;
        }

        return Texts.setPlaceholders(value, placeholderHandler);
    }
}
