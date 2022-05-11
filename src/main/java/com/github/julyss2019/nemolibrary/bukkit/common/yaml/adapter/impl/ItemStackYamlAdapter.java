package com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.item.ItemBuilder;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.DefaultValue;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Section;
import com.github.julyss2019.nemolibrary.bukkit.common.util.ItemUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Paths;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.YamlAdapter;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class ItemStackYamlAdapter implements YamlAdapter<ItemStack> {
    private static ItemStackYamlAdapter instance;

    public static ItemStackYamlAdapter getInstance() {
        if (instance == null) {
            instance = new ItemStackYamlAdapter();
        }

        return instance;
    }

    @Override
    public void set(@NotNull Section section, @NotNull String path, @Nullable ItemStack itemStack) {
        if (itemStack == null) {
            section.setByBukkit(path, null);
            return;
        }

        Section itemSection = section.getOrCreateSection(path);

        itemSection.setByBukkit("material", itemStack.getType());
        itemSection.setByBukkit("durability", itemStack.getDurability());
        itemSection.setByBukkit("display_name", ItemUtils.getItemDisplayName(itemStack));
        itemSection.setByBukkit("lores", ItemUtils.getItemLores(itemStack));
    }

    @Override
    public ItemStack get(@NotNull Section section, @NotNull String path) {
        Section itemSection = section.getSection(Paths.of(path), null);

        return new ItemBuilder()
                .material(itemSection.getString(Paths.of("material"), null))
                .durability(itemSection.getShort(Paths.of("durability"), DefaultValue.of((short) 0)))
                .displayName(itemSection.getString(Paths.of("display_name"), DefaultValue.of(null)))
                .setLores(itemSection.getStringList(Paths.of("lores"), DefaultValue.of(Collections.emptyList())))
                .build();
    }
}
