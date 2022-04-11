package com.github.julyss2019.nemolibrary.bukkit.common.yaml;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Yaml extends Section {
    private final YamlConfiguration bukkitYaml;

    private Yaml(@NotNull YamlConfiguration yaml) {
        super(yaml);

        this.bukkitYaml = yaml;
    }

    public void save(@NotNull File file) {
        Validator.checkNotNull(file, "file cannot be null");

        try {
            bukkitYaml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Yaml fromFile(@NotNull File file) {
        Validator.checkNotNull(file, "file cannot be null");

        return fromBukkitYaml(YamlConfiguration.loadConfiguration(file));
    }

    public static Yaml fromBukkitYaml(@NotNull YamlConfiguration yamlConfiguration) {
        Validator.checkNotNull(yamlConfiguration, "yamlConfiguration cannot be null");

        return new Yaml(yamlConfiguration);
    }
}
