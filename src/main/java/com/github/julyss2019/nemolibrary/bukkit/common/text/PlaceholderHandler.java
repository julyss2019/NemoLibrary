package com.github.julyss2019.nemolibrary.bukkit.common.text;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;

public interface PlaceholderHandler {
    /**
     * 处理占位符
     * @param key 键
     * @return 当返回值为 null 时将不处理占位符
     */
    @Nullable Object getValue(@NotNull String key);
}
