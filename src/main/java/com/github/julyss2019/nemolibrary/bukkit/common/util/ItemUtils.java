package com.github.julyss2019.nemolibrary.bukkit.common.util;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ItemUtils {
    public static @NotNull List<String> getItemLores(@Nullable ItemStack itemStack) {
        if (isValidItem(itemStack)) {
            return Optional.ofNullable(itemStack.getItemMeta().getLore()).orElse(new ArrayList<>());
        }

        return Collections.emptyList();
    }

    public static String getItemDisplayName(@Nullable ItemStack itemStack) {
        if (isValidItem(itemStack)) {
            return itemStack.getItemMeta().getDisplayName();
        }

        return null;
    }

    public static boolean isValidItem(@Nullable ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR;
    }

    public static @Nullable Material getMaterialById(int id) {
        if (id == 0) {
            return Material.AIR;
        }

        return (Material) ReflectUtils.invokeMethod(ReflectUtils.newInstance(Material.class),
                "getMaterial",
                id);
    }

    public static int getItemId(@Nullable ItemStack itemStack) {
        if (!isValidItem(itemStack)) {
            return 0;
        }

        return (int) ReflectUtils.getDeclaredFieldValue(itemStack, "getTypeId");
    }
}
