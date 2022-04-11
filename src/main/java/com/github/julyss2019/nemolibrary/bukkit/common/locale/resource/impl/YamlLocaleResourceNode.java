package com.github.julyss2019.nemolibrary.bukkit.common.locale.resource.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.google.common.base.Preconditions;

import java.util.List;

public class YamlLocaleResourceNode extends BaseLocaleResource {
    protected final YamlLocaleResource parent;
    protected final String node;

    public YamlLocaleResourceNode(@NotNull YamlLocaleResource parent, @NotNull String node) {
        super(parent.locale);

        Preconditions.checkNotNull(node, "node cannot be null");

        this.node = node;
        this.parent = parent;
    }

    private String getKeyWithNode(String key) {
        return node + "." + key;
    }

    @Override
    public YamlLocaleResourceNode getNode(@NotNull String key) {
        Validator.checkNotNull(key, "key cannot be null");

        return new YamlLocaleResourceNode(parent, getKeyWithNode(key));
    }

    @Override
    public List<String> getStringList(@NotNull String key) {
        Validator.checkNotNull(key, "key cannot be null");

        return parent.getStringList(getKeyWithNode(key));
    }

    @Override
    public String getString(@NotNull String key) {
        Validator.checkNotNull(key, "key cannot be null");

        return parent.getString(getKeyWithNode(key));
    }

    @Override
    public String getValue(@NotNull String key) {
        Validator.checkNotNull(key, "key cannot be null");

        return parent.getValue(getKeyWithNode(key));
    }
}
