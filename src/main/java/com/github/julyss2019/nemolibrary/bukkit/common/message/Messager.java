package com.github.julyss2019.nemolibrary.bukkit.common.message;

import com.github.julyss2019.nemolibrary.bukkit.common.text.Texts;
import com.github.julyss2019.nemolibrary.bukkit.common.util.ArrayUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Messager {
    public static void broadcastColoredMessages(@Nullable Character altColorChat, @NotNull String... messages) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            sendColoredMessages(player, altColorChat, messages);
        });
    }

    public static void broadcastColoredMessages(@NotNull String... messages) {
        broadcastColoredMessages(null, messages);
    }

    public static void sendColoredMessages(@NotNull CommandSender sender, @NotNull String... messages) {
        sendColoredMessages(sender, '&', messages);
    }

    public static void sendColoredMessages(@NotNull CommandSender sender, @Nullable Character altColorChar, @NotNull String... messages) {
        Validator.checkNotNull(sender, "sender cannot be null");
        Validator.checkNotNull(messages, "messages cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(messages, null), "messages cannot contains null element");

        for (String message : messages) {
            sender.sendMessage(Texts.getColoredText(message, altColorChar));
        }
    }
}
