package com.github.julyss2019.nemolibrary.bukkit.common.logger.layout;

import com.github.julyss2019.nemolibrary.bukkit.common.logger.MessageContext;

public interface Layout {
    String format(MessageContext context);
}
