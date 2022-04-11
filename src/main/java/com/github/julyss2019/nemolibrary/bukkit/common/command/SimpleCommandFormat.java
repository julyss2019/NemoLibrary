package com.github.julyss2019.nemolibrary.bukkit.common.command;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public class SimpleCommandFormat {
    public static String format(@NotNull ActiveCommand activeCommand) {
        StringBuilder tmp = new StringBuilder();

        tmp.append("/");
        tmp.append("<");

        activeCommand.getCommandBeforeList().forEach(activeCommandBefore -> {
            tmp.append(activeCommandBefore.getName());

            for (String alias : activeCommandBefore.getAliases()) {
                tmp.append("|").append(alias);
            }
        });

        tmp.append(">");
        tmp.append(" ");
        tmp.append("<");

        ActiveCommandBody commandBody = activeCommand.getCommandBody();

        tmp.append(commandBody.getName());

        for (String alias : commandBody.getAliases()) {
            tmp.append("|").append(alias);
        }

        tmp.append(">");

        String argumentsDescription = commandBody.getArgumentsDescription();

        if (argumentsDescription != null && !argumentsDescription.isEmpty()) {
            tmp.append(" ");
            tmp.append(commandBody.getArgumentsDescription());
        }

        tmp.append(" - ").append(commandBody.getDescription());
        return tmp.toString();
    }
}
