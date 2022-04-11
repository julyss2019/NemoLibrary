package com.github.julyss2019.nemolibrary.bukkit.common.command;

import com.github.julyss2019.nemolibrary.bukkit.NemoLibrary;
import com.github.julyss2019.nemolibrary.bukkit.common.command.annotation.CommandBefore;
import com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.LocaleResource;
import com.github.julyss2019.nemolibrary.bukkit.common.text.Texts;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.command.annotation.CommandBody;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.Logger;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

public class CommandFramework {
    private final Plugin plugin;
    private final Logger logger;
    private final CommandManager commandManager;
    private final CommandBefore pluginCommandBefore;
    private final Set<ActiveCommand> activeCommands = new HashSet<>();

    CommandFramework(Plugin plugin, NemoLibrary nemoLibrary) {
        this.plugin = plugin;
        this.commandManager = nemoLibrary.getCommandManager();
        this.logger = nemoLibrary.getPluginLogger();
        this.pluginCommandBefore = plugin.getClass().getAnnotation(CommandBefore.class);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public List<ActiveCommand> queryCommands(@NotNull String regex) {
        Validator.checkNotNull(regex, "regex cannot be null");

        return activeCommands.stream().filter(it -> it.isCommandQueryMatched(regex)).collect(Collectors.toList());
    }

    public void registerCommands(@NotNull CommandGroup commandGroup) {
        registerCommands(commandGroup, null);
    }

    /**
     * 注冊命令
     *
     * @param commandGroup
     */
    public void registerCommands(@NotNull CommandGroup commandGroup, @Nullable LocaleResource localeResource) {
        Validator.checkNotNull(commandGroup, "commandRoot cannot be null");

        List<CommandBefore> commandBeforeList = new ArrayList<>();

        // 插件层面的 @CommandBefore
        if (pluginCommandBefore != null) {
            commandBeforeList.add(pluginCommandBefore);
        }

        Set<ActiveCommand> activeCommands = processCommands(commandGroup.getClass(), commandGroup, commandBeforeList, localeResource);

        activeCommands.forEach(activeCommand -> {
            for (String prefix : activeCommand.getBukkitCommandPrefixes()) {
                if (!commandManager.isRegisteredCommandPrefix(prefix)) {
                    commandManager.registerCommandPrefix(prefix);
                }
            }

            logger.debug("command registered: " + activeCommand);
        });
        this.activeCommands.addAll(activeCommands);
    }

    /**
     * 实例化命令处理器
     * 根据内部类从外到内逐层实例化
     *
     * @param handlerClass    命令处理器类
     * @param commandGroupObj 命令对象，用于实例化第一层内部类
     * @return
     */
    private Object instantiateHandler(Class<?> handlerClass, CommandGroup commandGroupObj) {
        List<Class<?>> connectedClasses = new ArrayList<>();
        Class<?> tmp = handlerClass;
        boolean found = false;

        // 遍历并暂存上一级 Class
        while ((tmp = tmp.getDeclaringClass()) != null) {
            if (tmp.equals(commandGroupObj.getClass())) {
                found = true;
                break;
            }

            // 放入顶部
            connectedClasses.add(0, tmp);
        }

        if (!found) {
            throw new RuntimeException(String.format("cannot found top class %s", commandGroupObj.getClass().getName()));
        }

        Iterator<Class<?>> iterator = connectedClasses.iterator();
        Object obj = commandGroupObj;

        try {
            // 根据上下层级依次实例化内部类
            while (iterator.hasNext()) {
                Class<?> next = iterator.next();

                obj = next.getDeclaredConstructor(obj.getClass()).newInstance(obj);
            }

            return handlerClass.getDeclaredConstructor(obj.getClass()).newInstance(obj);
        } catch (Throwable e) {
            throw new RuntimeException(String.format("an exception occurred while instantiating class %s", handlerClass.getName()), e);
        }
    }

    /**
     * 处理命令
     *
     * @param clazz
     * @param commandGroup
     * @param tmpCommandBeforeList
     * @return
     */
    private Set<ActiveCommand> processCommands(Class<?> clazz, CommandGroup commandGroup, List<CommandBefore> tmpCommandBeforeList, @Nullable LocaleResource localeResource) {
        Set<ActiveCommand> result = new HashSet<>();

        CommandBefore commandBefore = clazz.getAnnotation(CommandBefore.class);

        if (commandBefore != null) {
            tmpCommandBeforeList.add(commandBefore);
        }

        CommandBody commandBody = clazz.getAnnotation(CommandBody.class);

        if (commandBody != null) {
            if (!CommandHandler.class.isAssignableFrom(clazz)) {
                throw new RuntimeException("class " + clazz + " not implement " + CommandHandler.class.getName());
            }

            List<ActiveCommandBefore> activeCommandBeforeList = new ArrayList<>();

            for (CommandBefore commandBefore1 : tmpCommandBeforeList) {
                activeCommandBeforeList.add(new ActiveCommandBefore(commandBefore1.name(), commandBefore1.aliases(), commandBefore1.permission()));
            }

            String progressedArgumentsDescription = commandBody.argumentsDescription();
            String progressedDescription = commandBody.description();

            if (localeResource != null) {
                progressedArgumentsDescription = Texts.setPlaceholders(progressedArgumentsDescription, localeResource);
                progressedDescription = Texts.setPlaceholders(progressedDescription, localeResource);
            }

            ActiveCommandBody activeCommandBody = new ActiveCommandBody(
                    commandBody.name(),
                    commandBody.aliases(),
                    progressedArgumentsDescription,
                    progressedDescription,
                    commandBody.permission(),
                    commandBody.targetSenders()
            );

            result.add(new ActiveCommand(activeCommandBeforeList, activeCommandBody, (CommandHandler) instantiateHandler(clazz, commandGroup), commandGroup));
        }

        // 递归所有子类
        for (Class<?> innerClass : clazz.getDeclaredClasses()) {
            result.addAll(processCommands(innerClass, commandGroup, tmpCommandBeforeList, localeResource));
        }

        return result;
    }

    /**
     * 注销所有命令
     */
    public void unregisterAllCommands() {
        activeCommands.clear();
        commandManager.clearInvalidCommandPrefixes();
    }

    /**
     * 注销命令
     *
     * @param command
     */
    public void unregisterCommand(@NotNull ActiveCommand command) {
        Validator.checkNotNull(command, "command cannot be null");

        activeCommands.remove(command);
        commandManager.clearInvalidCommandPrefixes();
    }

    /**
     * 获取已注册的命令
     *
     * @return
     */
    public Set<ActiveCommand> getRegisteredCommands() {
        return Collections.unmodifiableSet(activeCommands);
    }

    /**
     * 在所有命令被执行之前被调用
     *
     * @param sender
     * @param args
     * @return true 拦截 false 放行
     */
    public boolean onCommandPreprocess(CommandSender sender, String[] args) {
        return false;
    }

    /**
     * 在命令不存在时被调用；在前缀匹配时，但输入非预期输入时被调用
     *
     * @param sender
     * @param command
     */
    public void onCommandNotExists(CommandSender sender, String command) {
        sender.sendMessage("command not exists: /" + command);
        queryCommands(".*").forEach(activeCommand -> {
            sender.sendMessage(SimpleCommandFormat.format(activeCommand));
        });
    }

    /**
     * 在发送者不匹配时被调用
     *
     * @param sender
     * @param command
     */
    public void onSenderNotAllowed(CommandSender sender, String command, SenderType[] allowedSenders) {
        sender.sendMessage("this command /" + command + " only " + allowedSenders[0].name() + " can use");
    }

    /**
     * 在权限不足时被调用
     *
     * @param sender
     * @param command
     * @param permission
     */
    public void onPermissionDenied(CommandSender sender, String command, String permission) {
        sender.sendMessage("permission denied: " + permission);
    }
}
