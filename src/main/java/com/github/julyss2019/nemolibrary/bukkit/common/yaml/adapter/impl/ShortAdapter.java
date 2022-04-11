package com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Section;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Paths;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.YamlAdapter;

public class ShortAdapter implements YamlAdapter<Short> {
    private static ShortAdapter instance;

    public static ShortAdapter getInstance() {
        if (instance == null) {
            instance = new ShortAdapter();
        }

        return instance;
    }

    @Override
    public Short get(@NotNull Section section, @NotNull String path) {
        return (short) section.getInt(Paths.of(path), null);
    }

    @Override
    public void set(@NotNull Section section, @NotNull String path, @Nullable Short value) {
        section.setByBukkit(path, (int) value);
    }
}
