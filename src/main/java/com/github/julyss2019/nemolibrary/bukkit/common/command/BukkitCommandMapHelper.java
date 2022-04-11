package com.github.julyss2019.nemolibrary.bukkit.common.command;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Map;

public class BukkitCommandMapHelper {
    private static final CommandMap COMMAND_MAP;
    private static final Map<String, Command> KNOWN_COMMANDS;

    static {
        try {
            Field commandMapField = SimplePluginManager.class.getDeclaredField("commandMap");

            commandMapField.setAccessible(true);
            COMMAND_MAP = (CommandMap) commandMapField.get(Bukkit.getPluginManager());
            commandMapField.setAccessible(false);

            Field knownCommandsField = SimpleCommandMap.class.getDeclaredField("knownCommands");

            knownCommandsField.setAccessible(true);
            KNOWN_COMMANDS = (Map<String, Command>) knownCommandsField.get(COMMAND_MAP);
            knownCommandsField.setAccessible(false);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsCommand(@NotNull String prefix) {
        Validator.checkNotNull(prefix, "prefix cannot be null");

        return COMMAND_MAP.getCommand(prefix) != null;
    }

    public static void unregisterCommand(@NotNull String prefix) {
        Validator.checkNotNull(prefix, "prefix cannot be null");

        KNOWN_COMMANDS.remove(prefix);
    }

    public static void registerCommand(@NotNull String prefix, @NotNull Command command) {
        Validator.checkNotNull(prefix, "prefix cannot be null");

        if (existsCommand(prefix)) {
            throw new RuntimeException(String.format("command '%s' already registered", prefix));
        }

        COMMAND_MAP.register(prefix, command);
    }
}
