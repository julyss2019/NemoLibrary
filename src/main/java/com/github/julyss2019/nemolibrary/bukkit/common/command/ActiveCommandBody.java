package com.github.julyss2019.nemolibrary.bukkit.common.command;

import org.bukkit.command.CommandSender;

public class ActiveCommandBody {
    private final String name;
    private final String[] aliases;
    private final String argumentsDescription;
    private final String description;
    private final String permission;
    private final SenderType[] targetSenders;

    ActiveCommandBody(String name, String[] aliases, String argumentsDescription, String description, String permission, SenderType[] targetSenders) {
        this.name = name;
        this.aliases = aliases;
        this.argumentsDescription = argumentsDescription;
        this.description = description;
        this.permission = permission;
        this.targetSenders = targetSenders;
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getArgumentsDescription() {
        return argumentsDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getPermission() {
        return permission;
    }

    public SenderType[] getTargetSenders() {
        return targetSenders;
    }

    boolean isMatched(String argument) {
        if (argument.equalsIgnoreCase(name)) {
            return true;
        }

        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(argument)) {
                return true;
            }
        }

        return false;
    }

    boolean isSenderMatched(CommandSender sender) {
        SenderType senderType = SenderType.of(sender);

        for (SenderType targetSender : targetSenders) {
            if (senderType == targetSender) {
                return true;
            }
        }

        return false;
    }
}
