package com.github.julyss2019.nemolibrary.bukkit.common.command;

import com.github.julyss2019.nemolibrary.bukkit.NemoLibrary;
import com.github.julyss2019.nemolibrary.bukkit.common.util.StringUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

/*
在注册前缀时，首先检查 Bukkit 服务器中是否存在同样的 Prefix，如果存在则抛出异常。
当命令匹配到多个 CommandFramework 时，执行最先匹配到的。
 */
public class CommandManager {
    private final NemoLibrary nemoLibrary;
    private final Logger logger;
    private final Set<String> registeredCommandPrefixes = new HashSet<>();
    private final Set<CommandFramework> commandFrameworks = new HashSet<>();

    public CommandManager(@NotNull NemoLibrary plugin) {
        this.nemoLibrary = plugin;
        this.logger = plugin.getPluginLogger();
    }

    public void unregisterAllCommandFrameworks() {
        commandFrameworks.forEach(this::unregisterCommandFramework0);
        commandFrameworks.clear();
    }

    public void unregisterCommandFramework(@NotNull CommandFramework commandFramework) {
        Validator.checkNotNull(commandFramework, "commandFramework cannot be null");

        unregisterCommandFramework0(commandFramework);
        commandFrameworks.remove(commandFramework);
    }

    private void unregisterCommandFramework0(CommandFramework commandFramework) {
        commandFramework.unregisterAllCommands();
    }


    void clearInvalidCommandPrefixes() {
        Iterator<String> iterator = registeredCommandPrefixes.iterator();

        while (iterator.hasNext()) {
            String prefix = iterator.next();

            if (!isValidCommandPrefix(prefix)) {
                iterator.remove();
                BukkitCommandMapHelper.unregisterCommand(prefix);
                logger.debug("unregister command prefix: " + prefix);
            }
        }
    }

    boolean isValidCommandPrefix(@NotNull String prefix) {
        Validator.checkNotNull(prefix, "prefix cannot be null");

        for (CommandFramework commandFramework : commandFrameworks) {
            for (ActiveCommand command : commandFramework.getRegisteredCommands()) {
                if (command.getBukkitCommandPrefixes().contains(prefix)) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean isRegisteredCommandPrefix(@NotNull String prefix) {
        Validator.checkNotNull(prefix, "prefix cannot be null");

        return registeredCommandPrefixes.contains(prefix);
    }

    void registerCommandPrefix(@NotNull String prefix) {
        Validator.checkNotNull(prefix, "prefix cannot be null");

        if (isRegisteredCommandPrefix(prefix)) {
            throw new RuntimeException("prefix already registered");
        }

        if (BukkitCommandMapHelper.existsCommand(prefix)) {
            throw new RuntimeException(String.format("command '%s' already exists in bukkit command map", prefix));
        }

        registeredCommandPrefixes.add(prefix);
        BukkitCommandMapHelper.registerCommand(prefix, new BukkitCommand(prefix) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                dispatchCommand(sender, generateCommandLine(prefix, args));
                return true;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
                String[] commandLineArgs = new String[args.length + 1];

                commandLineArgs[0] = prefix;
                System.arraycopy(args, 0, commandLineArgs, 1, args.length);
                return completeTab(sender, commandLineArgs);
            }
        });

    }

    private static String generateCommandLine(String prefix, String[] args) {
        StringBuilder commandLineBuilder = new StringBuilder(prefix);

        for (String arg : args) {
            commandLineBuilder.append(" ").append(arg);
        }

        return commandLineBuilder.toString();
    }

    /*
    不能通过拼接参数的方式来处理，因为 Bukkit 会把 空 "" 参数传进来，而 split 后空参数就消失了
     */
    private List<String> completeTab(CommandSender sender, String[] commandLineArgs) {
        List<String> result = new ArrayList<>();
        int argsLength = commandLineArgs.length; // 参数总长度，包含已输入的args + 待补全的arg

        for (CommandFramework commandFramework : commandFrameworks) {
            for (ActiveCommand activeCommand : commandFramework.getRegisteredCommands()) {
                ActiveCommandBody commandBody = activeCommand.getCommandBody();
                List<ActiveCommandBefore> commandBeforeList = activeCommand.getCommandBeforeList();
                Iterator<ActiveCommandBefore> commandBeforeIterator = commandBeforeList.iterator();
                boolean fixedArgumentsMatched = true;
                boolean commandBodyMatched = false;

                for (int i = 0; i < commandLineArgs.length - 1; i++) {
                    String arg = commandLineArgs[i];

                    if (commandBeforeIterator.hasNext()) {
                        if (!commandBeforeIterator.next().isMatched(arg)) {
                            fixedArgumentsMatched = false;
                            break;
                        }
                    } else {
                        if (!commandBody.isMatched(arg)) {
                            fixedArgumentsMatched = false;
                        } else {
                            commandBodyMatched = true;
                        }

                        break;
                    }
                }

                // 固定参数不匹配
                if (!fixedArgumentsMatched) {
                    continue;
                }

                // 如果还有 CommandBefore 返回下一个 CommandBefore，否则返回 CommandBody
                if (commandBeforeIterator.hasNext()) {
                    ActiveCommandBefore commandBefore = commandBeforeIterator.next();

                    result.add(commandBefore.getName());
                    Collections.addAll(result, commandBefore.getAliases());
                } else if (!commandBodyMatched) { // CommandBody 未被使用，则补全 CommandBody
                    result.add(commandBody.getName());
                    Collections.addAll(result, commandBody.getAliases());
                } else { // CommandBody 被使用，则补全自定义
                    return activeCommand.completeTab(sender, new String[]{}, argsLength - commandBeforeList.size() - 1 - 1);
                }
            }
        }

        return result.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 调度执行命令
     * @param sender
     * @param commandLine
     */
    public void dispatchCommand(@NotNull CommandSender sender, @NotNull String commandLine) {
        Validator.checkNotNull(sender, "sender cannot be null");
        Validator.checkNotNull(commandLine, "commandLine cannot be null");

        logger.debug("dispatched command '%s' for player", commandLine, sender.toString());

        String[] commandLineArray = commandLine.split(" ");

        for (CommandFramework commandFramework : commandFrameworks) {
            for (ActiveCommand activeCommand : commandFramework.getRegisteredCommands()) {
                if (activeCommand.isCommandExecuteMatched(commandLine)) {
                    ActiveCommandBody commandBody = activeCommand.getCommandBody();
                    String[] handlerArgs = new String[commandLineArray.length - activeCommand.getCommandBeforeList().size() - 1];

                    System.arraycopy(commandLineArray, commandLineArray.length - handlerArgs.length, handlerArgs, 0, handlerArgs.length);

                    if (commandFramework.onCommandPreprocess(sender, handlerArgs)) {
                        logger.debug("command '%s' is intercepted by CommandFramework '%s'", activeCommand.toString(), commandFramework.toString());
                        return;
                    }

                    if (!commandBody.isSenderMatched(sender)) {
                        commandFramework.onSenderNotAllowed(sender, commandLine, commandBody.getTargetSenders());
                        return;
                    }

                    String per = commandBody.getPermission();

                    if (!StringUtils.isEmpty(per) && !sender.hasPermission(per)) {
                        commandFramework.onPermissionDenied(sender, commandLine, per);
                        return;
                    }

                    boolean executedResult = activeCommand.executeCommand(sender, handlerArgs);

                    logger.debug("command '%s' is executed for '%s' by '%s'", activeCommand.toString(), sender.toString(), activeCommand.getCommandInstance().toString());

                    if (executedResult) {
                        return;
                    }
                }
            }
        }

        for (CommandFramework commandFramework : commandFrameworks) {
            for (ActiveCommand registeredCommand : commandFramework.getRegisteredCommands()) {
                if (registeredCommand.getCommandBeforeList().get(0).isMatched(commandLineArray[0])) {
                    commandFramework.onCommandNotExists(sender, commandLine);
                    return;
                }
            }
        }
    }

    /**
     * 创建命令框架
     * @param plugin
     * @return
     */
    public CommandFramework createCommandFramework(@NotNull Plugin plugin) {
        Validator.checkNotNull(plugin, "plugin cannot be null");

        CommandFramework commandFramework = new CommandFramework(plugin, this.nemoLibrary);

        commandFrameworks.add(commandFramework);
        return commandFramework;
    }

    /**
     * 获取所有命令框架
     * @return
     */
    public Set<CommandFramework> getCommandFrameworks() {
        return Collections.unmodifiableSet(commandFrameworks);
    }
}
