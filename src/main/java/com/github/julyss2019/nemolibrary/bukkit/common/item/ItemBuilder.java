package com.github.julyss2019.nemolibrary.bukkit.common.item;

import com.github.julyss2019.nemolibrary.bukkit.common.util.ArrayUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.util.ItemUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ItemBuilder {
    private Material material;
    private int amount = 1;
    private short durability;
    private String displayName;
    private List<String> lores = new ArrayList<>();

    public ItemBuilder() {
    }

    /**
     * 设置材质
     * @param material
     * @return
     */
    public ItemBuilder material(@NotNull Material material) {
        Validator.checkNotNull(material, "material cannot be null");

        if (material == Material.AIR) {
            throw new RuntimeException("cannot use Material.AIR");
        }

        this.material = material;
        return this;
    }

    /**
     * 设置材质
     * @param materialName 材质名
     * @return
     */
    public ItemBuilder material(@NotNull String materialName) {
        Validator.checkNotNull(materialName, "materialName cannot be null");

        this.material = Optional.ofNullable(Material.getMaterial(materialName)).orElseThrow(() -> new RuntimeException("invalid material: " + materialName));
        return this;
    }

    /**
     * 设置材质
     * @param id 材质 ID
     * @return
     */
    public ItemBuilder material(int id) {
        this.material = ItemUtils.getMaterialById(id);
        return this;
    }

    /**
     * 设置数量
     * @param amount
     * @return
     */
    public ItemBuilder amount(int amount) {
        Validator.checkState(amount > 0, "amount must > 0");

        this.amount = amount;
        return this;
    }

    /**
     * 设置子 ID
     * @param durability
     * @return
     */
    public ItemBuilder durability(short durability) {
        this.durability = durability;
        return this;
    }

    /**
     * 设置物品名
     * @param displayName
     * @return
     */
    public ItemBuilder displayName(@Nullable String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * 设置 lores
     * @param lores
     * @return
     */
    public ItemBuilder setLores(@NotNull List<@NotNull String> lores) {
        Validator.checkNotNull(lores, "lores cannot be null");
        Validator.checkNotNull(!lores.contains(null), "lores cannot contains null element");

        this.lores = new ArrayList<>(lores);
        return this;
    }

    /**
     * 追加 lores
     * @param lores
     * @return
     */
    public ItemBuilder appendLores(@NotNull List<@NotNull String> lores) {
        Validator.checkNotNull(lores, "lores cannot be null");
        Validator.checkNotNull(!lores.contains(null), "lores cannot contains null element");

        this.lores.addAll(lores);
        return this;
    }

    /**
     * 追加 lores
     * @param lores
     * @return
     */
    public ItemBuilder appendLores(@NotNull String... lores) {
        Validator.checkNotNull(!ArrayUtils.containsElement(lores, null), "lores cannot contains null element");

        this.lores.addAll(Arrays.asList(lores));
        return this;
    }

    public ItemStack build() {
        if (material == null) {
            throw new RuntimeException("missing material");
        }

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemStack.setAmount(amount);
        itemStack.setDurability(durability);
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lores);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
