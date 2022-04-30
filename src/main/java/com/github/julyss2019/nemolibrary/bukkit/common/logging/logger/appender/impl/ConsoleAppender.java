package com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.appender.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.logging.Level;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.MessageContext;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.layout.Layout;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.Bukkit;

public class ConsoleAppender extends BaseAppender {
    public ConsoleAppender(@NotNull Layout layout, @NotNull Level threshold) {
        super(layout, threshold);
    }

    @Override
    public void execute(MessageContext messageContext) {
        super.execute(messageContext);

        Bukkit.getConsoleSender().sendMessage(getLayout().format(messageContext));
    }

    @Override
    public void close() {
        super.close();
    }
}
