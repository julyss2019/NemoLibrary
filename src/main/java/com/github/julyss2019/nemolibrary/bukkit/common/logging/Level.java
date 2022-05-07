package com.github.julyss2019.nemolibrary.bukkit.common.logging;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.ChatColor;

public enum Level {
    DEBUG(0, ChatColor.GRAY), INFO(1, ChatColor.WHITE), WARN(2, ChatColor.YELLOW), ERROR(3, ChatColor.RED);

    private final int level;
    private final ChatColor defaultColor;

    Level(int level, @NotNull ChatColor defaultColor) {
        this.level = level;
        this.defaultColor = defaultColor;
    }

    public ChatColor getDefaultColor() {
        return defaultColor;
    }

    public int getLevel() {
        return level;
    }
}