package com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.appender.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.logging.Level;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.MessageContext;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.appender.Appender;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.layout.Layout;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public abstract class BaseAppender implements Appender {
    private Layout layout;
    private Level threshold;
    private boolean closed;

    public BaseAppender(@NotNull Layout layout, @NotNull Level threshold) {
        setLayout(layout);
        setThreshold(threshold);
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void setThreshold(@NotNull Level level) {
        Validator.checkNotNull(level, "level cannot be null");

        this.threshold = level;
    }

    @Override
    public Level getThreshold() {
        return threshold;
    }

    @Override
    public void setLayout(@NotNull Layout layout) {
        Validator.checkNotNull(layout, "layout cannot be null");

        this.layout = layout;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public void close() {
        this.closed = true;
    }

    @Override
    public void execute(@NotNull MessageContext messageContext) {
        Validator.checkNotNull(messageContext, "messageContext cannot be null");

        if (isClosed()) {
            throw new RuntimeException("appender is closed");
        }
    }
}
