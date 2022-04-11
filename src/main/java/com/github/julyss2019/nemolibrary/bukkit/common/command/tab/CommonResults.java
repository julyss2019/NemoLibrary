package com.github.julyss2019.nemolibrary.bukkit.common.command.tab;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CommonResults {
    public static List<String> onlinePlayers() {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
    }
}
