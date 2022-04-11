package com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Section;
import com.github.julyss2019.nemolibrary.bukkit.common.util.EnumUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Paths;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.YamlAdapter;

public class EnumAdapter<E extends Enum<E>> implements YamlAdapter<E> {
    private final Class<E> clazz;

    public EnumAdapter(@NotNull Class<E> clazz) {
        Validator.checkNotNull(clazz, "clazz cannot be null");

        this.clazz = clazz;
    }

    @Override
    public E get(@NotNull Section section, @NotNull String path) {
        return EnumUtils.getEnum(clazz, section.getString(Paths.of(path), null));
    }

    @Override
    public void set(@NotNull Section section, @NotNull String path, @Nullable E value) {
        if (value == null) {
            section.setByBukkit(path, null);
            return;
        }

        section.setByBukkit(path, value.name());
    }
}
