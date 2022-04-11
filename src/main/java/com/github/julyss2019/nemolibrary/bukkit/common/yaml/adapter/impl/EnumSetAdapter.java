package com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Section;
import com.github.julyss2019.nemolibrary.bukkit.common.util.EnumUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Paths;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.YamlAdapter;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class EnumSetAdapter<E extends Enum<E>> implements YamlAdapter<EnumSet<E>> {
    private final Class<E> clazz;

    public EnumSetAdapter(@NotNull Class<E> clazz) {
        Validator.checkNotNull(clazz, "clazz cannot be null");

        this.clazz = clazz;
    }

    @Override
    public EnumSet<E> get(@NotNull Section section, @NotNull String path) {
        EnumSet<E> enumSet = EnumSet.noneOf(clazz);

        section.getStringList(Paths.of(path), null).forEach(s -> {
            enumSet.add(EnumUtils.getEnum(clazz, s));
        });

        return enumSet;
    }

    @Override
    public void set(@NotNull Section section, @NotNull String path, @Nullable EnumSet<E> value) {
        if (value == null) {
            section.setByBukkit(path, null);
            return;
        }

        section.setByBukkit(path, value.stream().map(Enum::name).distinct().collect(Collectors.toList()));
    }
}
