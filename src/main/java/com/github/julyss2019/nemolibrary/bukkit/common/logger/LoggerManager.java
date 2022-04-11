package com.github.julyss2019.nemolibrary.bukkit.common.logger;

import com.github.julyss2019.nemolibrary.bukkit.NemoLibrary;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.appender.Appender;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.appender.impl.ConsoleAppender;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.appender.impl.RollingFileAppender;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.layout.impl.PatternLayout;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LoggerManager {
    private final NemoLibrary plugin;
    private final Set<Logger> loggers = new HashSet<>();

    public LoggerManager(NemoLibrary plugin) {
        this.plugin = plugin;
    }

    public void unregisterAllLoggers() {
        loggers.forEach(this::unregisterLogger0);
    }

    public void unregisterLogger(@NotNull Logger logger) {
        Validator.checkNotNull(logger, "logger cannot be null");

        unregisterLogger0(logger);
        loggers.remove(logger);
    }

    private void unregisterLogger0(Logger logger) {
        logger.getAppenders().forEach(Appender::close);
    }

    public Set<Logger> getLoggers() {
        return Collections.unmodifiableSet(loggers);
    }

    public Logger createSimpleLogger(@NotNull Plugin plugin) {
        Validator.checkNotNull(plugin, "plugin cannot be null");

        Logger logger = createLogger(plugin);

        logger.addAppender(new ConsoleAppender(new PatternLayout("%c[%p] [%l] %m"), Level.INFO));
        logger.addAppender(new RollingFileAppender(new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss} [%l] %m"), Level.DEBUG,
                new File(plugin.getDataFolder(), "logs" + File.separator + "latest.log"), 3, "yyyy-MM-dd'.log'"));
        return logger;
    }

    public Logger createLogger(@NotNull Plugin plugin) {
        Validator.checkNotNull(plugin, "plugin cannot be null");

        Logger logger = new Logger(plugin);

        loggers.add(logger);
        return logger;
    }
}
