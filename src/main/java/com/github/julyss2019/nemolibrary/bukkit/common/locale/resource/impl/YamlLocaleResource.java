package com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.locale.Locale;
import com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.LocaleResource;
import com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.LocaleResourceNotExistsException;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.Yaml;

import java.io.File;
import java.util.List;

public class YamlLocaleResource extends BaseLocaleResource {
    private final Yaml yaml;
    private final String root;

    public YamlLocaleResource(@NotNull Locale locale, @NotNull File resourcesFolder) {
        this(locale, resourcesFolder, null, null);
    }

    public YamlLocaleResource(@NotNull Locale locale, @NotNull File resourcesFolder, @Nullable String name, @Nullable String root) {
        super(locale);

        Validator.checkNotNull(locale, "locale cannot be null");
        Validator.checkNotNull(resourcesFolder, "resourcesFolder cannot be null");
//        Validator.checkNotNull(root, "parentPath cannot be null");

        File file = new File(resourcesFolder, (name == null ? "" : name + "_") + locale.getLanguage() + "_" + locale.getCountry() + ".yml");

        if (!file.exists()) {
            throw new RuntimeException("locale resource file not found: " + file.getAbsolutePath());
        }

        this.yaml = Yaml.fromFile(file);
        this.root = root == null ? "" : root;
    }

    public String getRoot() {
        return root;
    }

    private String getWithRoot(String path) {
        if (root.equals("")) {
            return path;
        } else {
            return root + path;
        }
    }

    @Override
    public LocaleResource getNode(@NotNull String key) {
        Validator.checkNotNull(key, "path cannot be null");

        return new YamlLocaleResourceNode(this, root + key);
    }

    @Override
    public List<String> getStringList(@NotNull String key) {
        Validator.checkNotNull(key, "path cannot be null");

        String path = getWithRoot(key);

        if (!yaml.contains(path)) {
            throw new LocaleResourceNotExistsException(path);
        }

        return yaml.getStringList(path);
    }

    @Override
    public String getString(@NotNull String key) {
        Validator.checkNotNull(key, "key cannot be null");

        String path = getWithRoot(key);

        if (!yaml.contains(path)) {
            throw new LocaleResourceNotExistsException(path);
        }

        return yaml.getString(path);
    }

    @Override
    public String getValue(@NotNull String key) {
        Validator.checkNotNull(key, "key cannot be null");

        return getString(key);
    }
}
