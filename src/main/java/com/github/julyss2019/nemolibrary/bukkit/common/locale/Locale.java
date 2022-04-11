package com.github.julyss2019.nemolibrary.bukkit.common.locale;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public class Locale {
    public static final Locale ZH_CN = new Locale("zh", "cn");

    private final String country;
    private final String language;

    public Locale(@NotNull String language, @NotNull String country) {
        Validator.checkNotNull(language, "language cannot be null");
        Validator.checkNotNull(country, "country cannot be null");

        this.country = country;
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }
}
