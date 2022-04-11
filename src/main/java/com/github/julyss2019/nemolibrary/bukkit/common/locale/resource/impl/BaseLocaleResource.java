package com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.locale.Locale;
import com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.LocaleResource;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public abstract class BaseLocaleResource implements LocaleResource {
    protected final Locale locale;

    public BaseLocaleResource(@NotNull Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
