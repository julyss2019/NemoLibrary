package com.github.julyss2019.nemolibrary.bukkit.common.text;

import java.util.List;
import java.util.stream.Collectors;

public interface TextHandler {
    String handle(String text);

    default List<String> handle(List<String> list) {
        return list.stream().map(this::handle).collect(Collectors.toList());
    }
}
