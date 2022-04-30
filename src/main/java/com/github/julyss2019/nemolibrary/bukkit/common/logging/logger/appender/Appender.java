package com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.appender;

import com.github.julyss2019.nemolibrary.bukkit.common.logging.Level;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.layout.Layout;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.MessageContext;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public interface Appender {
    void execute(@NotNull MessageContext messageContext);

    void setThreshold(@NotNull Level level);

    Level getThreshold();

    void setLayout(@NotNull Layout layout);

    Layout getLayout();

    void close();

    boolean isClosed();
}
