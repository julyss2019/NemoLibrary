package com.github.julyss2019.nemolibrary.bukkit.common.util;

import com.github.julyss2019.nemolibrary.bukkit.NemoLibrary;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;

public class BungeeUtils {
    public static NemoLibrary plugin;

    public static void setPlugin(@NotNull NemoLibrary plugin) {
        Validator.checkNotNull(plugin, "plugin cannot be null");

        if (BungeeUtils.plugin != null) {
            throw new UnsupportedOperationException();
        }

        BungeeUtils.plugin = plugin;
    }

    public static void connectServer(@NotNull Player player, @NotNull String server) {
        Validator.checkNotNull(player, "player cannot be null");
        Validator.checkNotNull(server, "server cannot be null");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
