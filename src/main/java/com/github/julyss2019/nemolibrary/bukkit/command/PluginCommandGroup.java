package com.github.julyss2019.nemolibrary.bukkit.command;

import com.github.julyss2019.nemolibrary.bukkit.NemoLibrary;
import com.github.julyss2019.nemolibrary.bukkit.common.command.Arguments;
import com.github.julyss2019.nemolibrary.bukkit.common.command.CommandGroup;
import com.github.julyss2019.nemolibrary.bukkit.common.command.CommandHandler;
import com.github.julyss2019.nemolibrary.bukkit.common.command.SimpleCommandFormat;
import com.github.julyss2019.nemolibrary.bukkit.common.command.annotation.CommandBody;
import com.github.julyss2019.nemolibrary.bukkit.common.message.Messager;
import org.bukkit.command.CommandSender;

//@CommandBefore(name = "plugin")
public class PluginCommandGroup implements CommandGroup {
    private final NemoLibrary nemoLibrary;

    public PluginCommandGroup(NemoLibrary nemoLibrary) {
        this.nemoLibrary = nemoLibrary;
    }

    @CommandBody(name = "version", aliases = "ver", argumentsDescription = "", description = "show version", permission = "NemoLibrary.version")
    public class Version implements CommandHandler {
        @Override
        public boolean onExecuteCommand(CommandSender sender, Arguments arguments) {
            sender.sendMessage("version: " + nemoLibrary.getDescription().getVersion());
            return true;
        }
    }

    @CommandBody(name = "help", aliases = "h", argumentsDescription = "", description = "show help", permission = "")
    public class Helper implements CommandHandler {
        @Override
        public boolean onExecuteCommand(CommandSender sender, Arguments arguments) {
            nemoLibrary.getCommandFramework().queryCommands("nemolibrary .*").forEach(activeCommand -> {
                Messager.sendColoredMessages(sender, SimpleCommandFormat.format(activeCommand));
            });
            return true;
        }
    }
}
