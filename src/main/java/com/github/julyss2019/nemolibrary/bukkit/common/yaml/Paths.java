package com.github.julyss2019.nemolibrary.bukkit.common.yaml;

import com.github.julyss2019.nemolibrary.bukkit.common.util.ArrayUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public class Paths {
    private final String[] paths;

    private Paths(String[] paths) {
        this.paths = paths;
    }

    public String[] getPaths() {
        return paths;
    }

    public static Paths of(@NotNull String... paths) {
        Validator.checkNotNull(paths, "paths cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(paths, null), "paths cannot contains null element");

        return new Paths(paths);
    }
}
