package com.github.julyss2019.nemolibrary.bukkit.common.command;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public enum SenderType {
    PLAYER, CONSOLE;

    public static SenderType of(@NotNull CommandSender sender) {
        Validator.checkNotNull(sender, "sender cannot be null");

        if (sender instanceof Player) {
            return PLAYER;
        } else if (sender instanceof ConsoleCommandSender) {
            return CONSOLE;
        }

        throw new UnsupportedOperationException("unknown command sender");
    }
}
