package com.github.julyss2019.nemolibrary.bukkit.common.logger;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class MessageContext {
    private final Plugin plugin;
    private final Level level;
    private final String msg;
    private final ChatColor color;

    public MessageContext(@NotNull Plugin plugin, @NotNull Level level, @NotNull String msg, @NotNull ChatColor color) {
        this.plugin = plugin;
        this.level = level;
        this.msg = msg;
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Level getLevel() {
        return level;
    }

    public String getMsg() {
        return msg;
    }
}
