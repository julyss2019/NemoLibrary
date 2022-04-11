package com.github.julyss2019.nemolibrary.bukkit.common.command;

import com.github.julyss2019.nemolibrary.bukkit.common.util.ArrayUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 已注册的命令
 */
public class ActiveCommand {
    private final List<ActiveCommandBefore> commandBeforeList; // 命令前缀
    private final ActiveCommandBody commandBody;
    private final CommandHandler commandHandler; // 命令处理器：处理命令和 Tab
    private final CommandGroup commandGroupInstance;
    private String fixedCommandString; // 命令固定部分

    ActiveCommand(List<ActiveCommandBefore> commandBeforeList, ActiveCommandBody commandBody, CommandHandler commandHandler, CommandGroup commandGroupInstance) {
        this.commandBeforeList = new ArrayList<>(commandBeforeList);
        this.commandBody = commandBody;
        this.commandHandler = commandHandler;
        this.commandGroupInstance = commandGroupInstance;

        setFixedCommand();
    }

    private void setFixedCommand() {
        StringBuilder tmp = new StringBuilder();

        for (ActiveCommandBefore activeCommandBefore : commandBeforeList) {
            tmp.append(activeCommandBefore.getName()).append(" ");
        }

        tmp.append(commandBody.getName());

        this.fixedCommandString = tmp.toString();
    }

    public List<String> getBukkitCommandPrefixes() {
        List<String> prefixes = new ArrayList<>();

        if (!commandBeforeList.isEmpty()) {
            ActiveCommandBefore firstCommandBefore = commandBeforeList.get(0);

            prefixes.add(firstCommandBefore.getName());
            prefixes.addAll(Arrays.asList(firstCommandBefore.getAliases()));
        } else {
            prefixes.add(commandBody.getName());
            prefixes.addAll(Arrays.asList(commandBody.getAliases()));
        }

        return prefixes;
    }

    public List<String> completeTab(@NotNull CommandSender sender, @NotNull String[] args, int index) {
        Validator.checkNotNull(sender, "sender cannot be null");
        Validator.checkNotNull(args, "args cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(args, null), "args cannot contains null element");

        return commandHandler.onCompleteTab(sender, Arguments.of(args), index);
    }

    public boolean executeCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        Validator.checkNotNull(sender, "sender cannot be null");
        Validator.checkNotNull(args, "args cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(args, null), "args cannot contains null element");

        return commandHandler.onExecuteCommand(sender, Arguments.of(args));
    }

    /**
     * 命令是否被查询到
     * @param regex
     * @return
     */
    public boolean isCommandQueryMatched(@NotNull String regex) {
        Validator.checkNotNull(regex, "regex cannot be null");

        return fixedCommandString.matches(regex);
    }

    /**
     * 命令是否可被执行
     *
     * @param message
     * @return
     */
    public boolean isCommandExecuteMatched(@NotNull String message) {
        Validator.checkNotNull(message, "message cannot be null");

        String[] commandLineArray = message.split(" ");

        // 至少要得到 Befores.length + CommandBody.firstArg 的长度
        if (commandLineArray.length < commandBeforeList.size() + 1) {
            return false;
        }

        for (int commandBeforeIndex = 0, argumentIndex = 0; commandBeforeIndex < commandBeforeList.size(); commandBeforeIndex++, argumentIndex++) {
            String argument = commandLineArray[argumentIndex];
            ActiveCommandBefore commandBefore = commandBeforeList.get(commandBeforeIndex);

            if (!argument.equalsIgnoreCase(commandBefore.getName())) {
                boolean matched = false;

                for (String alias : commandBefore.getAliases()) {
                    if (argument.equalsIgnoreCase(alias)) {
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    return false;
                }
            }
        }

        String bodyFirstArgument = commandLineArray[commandBeforeList.size()];

        if (!bodyFirstArgument.equalsIgnoreCase(commandBody.getName())) {
            for (String alias : commandBody.getAliases()) {
                if (bodyFirstArgument.equalsIgnoreCase(alias)) {
                    return true;
                }
            }

            return false;
        }

        return true;
    }

    public CommandGroup getCommandInstance() {
        return commandGroupInstance;
    }

    public List<ActiveCommandBefore> getCommandBeforeList() {
        return Collections.unmodifiableList(commandBeforeList);
    }

    public ActiveCommandBody getCommandBody() {
        return commandBody;
    }

    @Override
    public String toString() {
        return "RegisteredCommand{" +
                "commandBeforeList=" + commandBeforeList +
                ", commandBody=" + commandBody +
                ", commandInstance=" + commandGroupInstance +
                '}';
    }
}
