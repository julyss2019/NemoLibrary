package com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.layout;

import com.github.julyss2019.nemolibrary.bukkit.common.logging.MessageContext;

public interface Layout {
    String format(MessageContext context);
}
