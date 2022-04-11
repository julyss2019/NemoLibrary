package com.github.julyss2019.nemolibrary.bukkit.common.command;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

/**
 * 命令处理器
 * 用于编写和 Tab 的具体实现
 */
public interface CommandHandler {
    boolean onExecuteCommand(CommandSender sender, Arguments arguments);

    default List<String> onCompleteTab(CommandSender sender, Arguments arguments, int index) {
        return Collections.emptyList();
    }
}
