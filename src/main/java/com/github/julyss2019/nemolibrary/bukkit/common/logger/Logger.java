package com.github.julyss2019.nemolibrary.bukkit.common.logger;

import com.github.julyss2019.nemolibrary.bukkit.common.logger.appender.Appender;
import com.github.julyss2019.nemolibrary.bukkit.common.util.ArrayUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class Logger {
    private final Plugin plugin;
    private final List<Appender> appenders = new ArrayList<>();
    private final Map<Level, ChatColor> levelColorMap = new HashMap<>();

    Logger(Plugin plugin) {
        this.plugin = plugin;
        setDefaultLevelColors();
    }

    private void setDefaultLevelColors() {
        for (Level level : Level.values()) {
            levelColorMap.put(level, level.getDefaultColor());
        }
    }

    public ChatColor getLevelColor(@NotNull Level level) {
        Validator.checkNotNull(level, "level cannot be null");

        return levelColorMap.get(level);
    }

    public void setLevelColor(@NotNull Level level, @NotNull ChatColor color) {
        Validator.checkNotNull(level, "level cannot be null");
        Validator.checkNotNull(color, "color cannot be null");

        levelColorMap.put(level, color);
    }

    public void addAppender(@NotNull Appender appender) {
        Validator.checkNotNull(appender, "appender cannot be null");

        appenders.add(appender);
    }

    public List<Appender> getAppenders() {
        return Collections.unmodifiableList(appenders);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void debug(@NotNull String msg, String... args) {
        Validator.checkNotNull(msg, "msg cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(args, null), "args cannot contains null");

        log(Level.DEBUG, msg, args);
    }

    public void warning(@NotNull String msg, String... args) {
        Validator.checkNotNull(msg, "msg cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(args, null), "args cannot contains null");

        log(Level.WARNING, msg, args);
    }

    public void info(@NotNull String msg, String... args) {
        Validator.checkNotNull(msg, "msg cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(args, null), "args cannot contains null");

        log(Level.INFO, msg, args);
    }

    public void log(@NotNull Level level, @NotNull String msg, String... args) {
        Validator.checkNotNull(msg, "msg cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(args, null), "args cannot contains null");

        String placeholderFormattedMsg = LogPlaceholderFormatter.format(msg, args);

        for (Appender appender : appenders) {
            if (level.getLevel() >= appender.getThreshold().getLevel()) {
                appender.execute(new MessageContext(plugin, level, placeholderFormattedMsg, levelColorMap.get(level)));
            }
        }
    }

    public ConsoleCommandSender getConsoleSender() {
        return Bukkit.getConsoleSender();
    }
}
