package com.github.julyss2019.nemolibrary.bukkit.common.command;

import com.github.julyss2019.nemolibrary.bukkit.common.util.PlayerUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.util.StringUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Arguments {
    private final String[] args;

    private Arguments(String[] args) {
        this.args = args;
    }

    static Arguments of(@NotNull String[] args) {
        Validator.checkNotNull(args, "args cannot be null");

        return new Arguments(args);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= args.length) {
            throw new RuntimeException(String.format("invalid index: %d", index));
        }
    }

    public boolean isOnlinePlayer(int index) {
        checkIndex(index);

        return PlayerUtils.isOnlinePlayer(get0(index));
    }

    public Player getOnlinePlayer(int index) {
        checkIndex(index);

        return Bukkit.getPlayer(get0(index));
    }

    public int length() {
        return args.length;
    }

    private String get0(int index) {
        return args[index];
    }

    public String get(int index) {
        checkIndex(index);

        return get0(index);
    }

    public boolean isInt(int index) {
        checkIndex(index);

        return StringUtils.isInteger(get0(index));
    }

    public int getInt(int index) {
        checkIndex(index);

        return Integer.parseInt(get0(index));
    }
}
