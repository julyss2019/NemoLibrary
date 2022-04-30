package com.github.julyss2019.nemolibrary.bukkit;

import com.github.julyss2019.nemolibrary.bukkit.api.NemoLibraryAPI;
import com.github.julyss2019.nemolibrary.bukkit.command.PluginCommandGroup;
import com.github.julyss2019.nemolibrary.bukkit.common.command.CommandFramework;
import com.github.julyss2019.nemolibrary.bukkit.common.command.CommandManager;
import com.github.julyss2019.nemolibrary.bukkit.common.command.annotation.CommandBefore;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.Logger;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.LoggerDailyFileAppenderAutoFlushTask;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.LoggerManager;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.LogManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

@CommandBefore(name = "nemolibrary", aliases = {"nemolib"})
public class NemoLibrary extends JavaPlugin {
    private static NemoLibrary instance;
    private Logger logger;
    private LoggerManager loggerManager;
    private LogManager logManager;
    private CommandManager commandManager;
    private CommandFramework commandFramework;

    @Override
    public void onEnable() {
        instance = this;
        NemoLibraryAPI.setPlugin(this);
        this.loggerManager = new LoggerManager(this);
        this.logManager = new LogManager(this);
        this.logger = loggerManager.createSimpleLogger(this);

        this.commandManager = new CommandManager(this);
        this.commandFramework = commandManager.createCommandFramework(this);

        commandFramework.registerCommands(new PluginCommandGroup(this));
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        runTasks();
        logger.info("插件初始化完毕.");
    }

    @Override
    public void onDisable() {
        logger.info("插件被卸载.");
        HandlerList.unregisterAll(this);
        commandManager.unregisterAllCommandFrameworks();
        loggerManager.unregisterAllLoggers();
    }

    private void runTasks() {
        new LoggerDailyFileAppenderAutoFlushTask(this).runTaskTimer(this, 0L, 20L);
        new com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.LoggerDailyFileAppenderAutoFlushTask(this).runTaskTimer(this, 0L, 20L);
    }

    public CommandFramework getCommandFramework() {
        return commandFramework;
    }

    public Logger getPluginLogger() {
        return logger;
    }

    public static NemoLibrary getInstance() {
        return instance;
    }

    public LogManager getLogManager() {
        return logManager;
    }

    public LoggerManager getLoggerManager() {
        return loggerManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
