package com.github.julyss2019.nemolibrary.bukkit.common.text;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public class ColoredTextHandler implements TextHandler {
    private Character altChar = '&';

    public ColoredTextHandler() {
    }

    public ColoredTextHandler(@NotNull Character altChar) {
        this.altChar = altChar;
    }

    @Override
    public String handle(String text) {
        return Texts.getColoredText(text, altChar);
    }
}
