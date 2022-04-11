package com.github.julyss2019.nemolibrary.bukkit.api;

import com.github.julyss2019.nemolibrary.bukkit.NemoLibrary;
import com.github.julyss2019.nemolibrary.bukkit.common.command.CommandFramework;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.LoggerManager;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.command.CommandManager;

public class NemoLibraryAPI {
    private static NemoLibrary plugin;

    public static void setPlugin(@NotNull NemoLibrary plugin) {
        if (NemoLibraryAPI.plugin != null) {
            throw new UnsupportedOperationException();
        }

        NemoLibraryAPI.plugin = plugin;
    }

    public static CommandManager getCommandManager() {
        return plugin.getCommandManager();
    }

    public static LoggerManager getLoggerManager() {
        return plugin.getLoggerManager();
    }
}
