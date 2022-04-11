package com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter;

import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Section;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;

public interface YamlAdapter<T> {
    T get(@NotNull Section section, @NotNull String path);

    void set(@NotNull Section section, @NotNull String path, @Nullable T value);
}
