package com.github.julyss2019.nemolibrary.bukkit.common.logger;

import com.github.julyss2019.nemolibrary.bukkit.NemoLibrary;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.appender.impl.FileAppender;
import com.github.julyss2019.nemolibrary.bukkit.common.task.async.TaskLock;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class LoggerDailyFileAppenderAutoFlushTask extends BukkitRunnable {
    private final LoggerManager loggerManager;
    private final Map<FileAppender, Long> lastFlushedMap = new HashMap<>();
    private final TaskLock taskLock = new TaskLock();

    public LoggerDailyFileAppenderAutoFlushTask(@NotNull NemoLibrary plugin) {
        this.loggerManager = plugin.getLoggerManager();
    }

    @Override
    public void run() {
        if (taskLock.isLocked()) {
            return;
        }

        taskLock.lock();
        try {
            loggerManager.getLoggers().forEach(logger -> {
                logger.getAppenders().forEach(appender -> {
                    if (appender instanceof FileAppender) {
                        FileAppender fileAppender = (FileAppender) appender;

                        if (!lastFlushedMap.containsKey(fileAppender)) {
                            lastFlushedMap.put(fileAppender, System.currentTimeMillis());
                        }

                         if (System.currentTimeMillis() - lastFlushedMap.get(fileAppender) >= fileAppender.getFlushInterval() * 1000L) {
                             fileAppender.flush();
                             lastFlushedMap.put(fileAppender, System.currentTimeMillis());
                         }
                    }
                });
            });
        } finally {
            taskLock.unlock();
        }
    }
}
