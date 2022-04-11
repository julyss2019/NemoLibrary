package com.github.julyss2019.nemolibrary.bukkit.common.command;

public class ActiveCommandBefore {
    private final String name;
    private final String[] aliases;
    private final String permission;

    public ActiveCommandBefore(String name, String[] aliases, String permission) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getPermission() {
        return permission;
    }

    boolean isMatched(String argument) {
        if (name.equalsIgnoreCase(argument)) {
            return true;
        }

        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(argument)) {
                return true;
            }
        }

        return false;
    }
}
