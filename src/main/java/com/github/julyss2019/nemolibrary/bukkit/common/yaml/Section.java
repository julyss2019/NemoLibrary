package com.github.julyss2019.nemolibrary.bukkit.common.yaml;


import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.YamlAdapter;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl.EnumAdapter;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl.EnumSetAdapter;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl.ItemStackYamlAdapter;
import com.github.julyss2019.nemolibrary.bukkit.common.yaml.adapter.impl.ShortAdapter;
import com.google.common.base.Preconditions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class Section {
    public interface Parser<T> {
        T parse(@NotNull String path);
    }

    private final ConfigurationSection bukkitSection;
    private final String currentPath;

    protected Section(@NotNull ConfigurationSection bukkitSection) {
        Validator.checkNotNull(bukkitSection, "bukkitSection cannot be null");

        this.bukkitSection = bukkitSection;
        this.currentPath = bukkitSection.getCurrentPath();
    }

    public static Section fromBukkitSection(@NotNull ConfigurationSection section) {
        Validator.checkNotNull(section, "section cannot be null");

        return new Section(section);
    }

    public String getName() {
        return this.bukkitSection.getName();
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public ConfigurationSection getBukkitSection() {
        return this.bukkitSection;
    }

    public boolean contains(@NotNull String path) {
        Validator.checkNotNull(path, "path cannot be null");

        return bukkitSection.contains(path);
    }

    public boolean contains(@NotNull Paths paths) {
        Validator.checkNotNull(paths, "paths cannot be null");

        for (String path : paths.getPaths()) {
            if (this.bukkitSection.contains(path)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取当前路径下的所有子节点
     *
     * @return
     */
    public Set<Section> getSubSections() {
        Set<Section> sections = new HashSet<>();

        this.bukkitSection.getKeys(false).forEach(key -> {
            sections.add(new Section(this.bukkitSection.getConfigurationSection(key)));
        });
        return sections;
    }

    public ItemStack getItemStack(@NotNull String path) {
        return getItemStack(Paths.of(path), null);
    }

    public ItemStack getItemStack(@NotNull Paths paths, @Nullable DefaultValue<ItemStack> def) {
        return getByAdapter(paths, ItemStackYamlAdapter.getInstance(), def);
    }

    public <E extends Enum<E>> EnumSet<E> getEnumSet(@NotNull String path, @Nullable Class<E> clazz) {
        return getEnumSet(Paths.of(path), clazz, null);
    }

    public <E extends Enum<E>> EnumSet<E> getEnumSet(@NotNull Paths paths, @NotNull Class<E> clazz, @Nullable DefaultValue<EnumSet<E>> def) {
        return getByAdapter(paths, new EnumSetAdapter<>(clazz), def);
    }

    public <E extends Enum<E>> E getEnum(@NotNull String path, @NotNull Class<E> clazz) {
        return getEnum(Paths.of(path), clazz, null);
    }

    public <E extends Enum<E>> E getEnum(@NotNull Paths paths, @NotNull Class<E> clazz, @Nullable DefaultValue<E> def) {
        return getByAdapter(paths, new EnumAdapter<>(clazz), def);
    }

    public Section getOrCreateSection(@NotNull String path) {
        if (contains(path)) {
            return fromBukkitSection(bukkitSection.getConfigurationSection(path));
        }

        return fromBukkitSection(bukkitSection.createSection(path));
    }

    public Section getSection(@NotNull String path) {
        return getSection(Paths.of(path), null);
    }

    public Section getSection(@NotNull Paths paths, @Nullable DefaultValue<Section> def) {
        return getValue(paths, path -> {
            ConfigurationSection bukkitSection = Section.this.bukkitSection.getConfigurationSection(path);

            if (bukkitSection == null) {
                return null;
            }

            return Section.fromBukkitSection(bukkitSection);
        }, def);
    }

    public List<Integer> getIntegerList(@NotNull String path) {
        return getIntegerList(Paths.of(path), null);
    }

    public List<Integer> getIntegerList(@NotNull Paths paths, @Nullable DefaultValue<List<Integer>> def) {
        return getByBukkit(paths, def);
    }

    public List<String> getStringList(@NotNull String path) {
        return getStringList(Paths.of(path), null);
    }

    public List<String> getStringList(@NotNull Paths paths, @Nullable DefaultValue<List<String>> def) {
        return getByBukkit(paths, def);
    }

    public short getShort(@NotNull String path) {
        return getShort(Paths.of(path), null);
    }

    public short getShort(@NotNull Paths paths, @Nullable DefaultValue<Short> def) {
        return getByAdapter(paths, ShortAdapter.getInstance(), def);
    }

    public int getInt(@NotNull String path) {
        return getInt(Paths.of(path), null);
    }

    public int getInt(@NotNull Paths paths, @Nullable DefaultValue<Integer> def) {
        return getByBukkit(paths, def);
    }

    public double getDouble(@NotNull String path) {
        return getDouble(Paths.of(path), null);
    }

    public double getDouble(@NotNull Paths paths, @Nullable DefaultValue<Double> def) {
        return getValue(paths, new Parser<Double>() {
            @Override
            public Double parse(String path) {
                if (bukkitSection.isInt(path)) {
                    return (double) bukkitSection.getInt(path);
                }

                return bukkitSection.getDouble(path);
            }
        }, def);
    }

    public float getFloat(@NotNull String path) {
        return getFloat(Paths.of(path), null);
    }

    public float getFloat(@NotNull Paths paths, @Nullable DefaultValue<Float> def) {
        return getByBukkit(paths, def);
    }

    public String getString(@NotNull String path) {
        return getString(Paths.of(path), null);
    }

    public String getString(@NotNull Paths paths, @Nullable DefaultValue<String> def) {
        return getByBukkit(paths, def);
    }

    public boolean getBoolean(@NotNull String path) {
        return getBoolean(Paths.of(path), null);
    }

    public boolean getBoolean(@NotNull Paths paths, @Nullable DefaultValue<Boolean> def) {
        return getByBukkit(paths, def);
    }

    public Set<String> getStringSet(@NotNull String path) {
        return getStringSet(Paths.of(path), null);
    }

    public Set<String> getStringSet(@NotNull Paths paths, @Nullable DefaultValue<Set<String>> def) {
        return new HashSet<>(getByBukkit(paths, def));
    }

    public <T> T getByBukkit(@NotNull String path) {
        return getByBukkit(Paths.of(path), null);
    }

    public <T> T getByBukkit(@NotNull Paths paths, @Nullable DefaultValue<T> def) {
        Validator.checkNotNull(paths, "paths cannot be null");

        return getValue(paths, path -> {
            try {
                return  (T) bukkitSection.get(path);
            } catch (Exception exception) {
                throw new RuntimeException(String.format("an exception occurred while get value from '%s'", currentPath + "." + path), exception);
            }
        }, def);
    }

    public <T> T getByAdapter(@NotNull Paths paths, @Nullable YamlAdapter<T> adapter, @Nullable DefaultValue<T> def) {
        Validator.checkNotNull(paths, "paths cannot be null");
        Validator.checkNotNull(adapter, "adapter cannot be null");

        return getValue(paths, path -> adapter.get(Section.this, path), def);
    }

    public void setByBukkit(@NotNull String path, @Nullable Object value) {
        Validator.checkNotNull(path, "path cannot be null");

        bukkitSection.set(path, value);
    }

    public <T> void setByAdapter(@NotNull String path, @Nullable T value, @NotNull YamlAdapter<T> adapter) {
        Validator.checkNotNull(path, "path cannot be null");
        Validator.checkNotNull(adapter, "adapter cannot be null");

        adapter.set(this, path, value);
    }

    private <T> T getValue(@NotNull Paths paths, @NotNull Parser<T> parser, DefaultValue<T> defValue) {
        Object value = null;

        for (String path : paths.getPaths()) {
            if (this.bukkitSection.contains(path)) {
                value = parser.parse(path);
                break;
            }
        }

        if (value == null) {
            if (defValue != null) {
                return defValue.getValue();
            }

            throw new RuntimeException(String.format("cannot find valid value: %s%s", this.bukkitSection.getCurrentPath(), Arrays.toString(paths.getPaths())));
        }

        return (T) value;
    }
}
