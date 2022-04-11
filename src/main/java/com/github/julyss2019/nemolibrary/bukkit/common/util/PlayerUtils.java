package com.github.julyss2019.nemolibrary.bukkit.common.util;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerUtils {
    public static void giveItemIfInventoryFullDropItem(@NotNull Player player, ItemStack itemStack) {
        if (ItemUtils.isValidItem(itemStack)) {
            PlayerInventory inventory = player.getInventory();

            if (inventory.firstEmpty() == -1) {
                player.getWorld().dropItem(player.getLocation(), itemStack);
            } else {
                inventory.addItem(itemStack);
            }
        }
    }

    public static int getInventoryAvailableSlotCount(@NotNull Player player) {
        int total = 0;
        PlayerInventory playerInventory = player.getInventory();

        for (int i = 0; i < playerInventory.getSize(); i++) {
            ItemStack itemStack = playerInventory.getItem(i);

            if (!ItemUtils.isValidItem(itemStack)) {
                total += 1;
            }
        }

        return total;
    }

    public static boolean isOnlinePlayer(@NotNull String name) {
        Validator.checkNotNull(name, "name cannot be null");

        return Bukkit.getPlayer(name) != null;
    }
}
