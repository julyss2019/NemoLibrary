package com.github.julyss2019.nemolibrary.bukkit.common.text;

import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class Texts {
    public static String getColoredText(@NotNull String text) {
        return getColoredText(text, '&');
    }

    public static String getColoredText(@NotNull String text, @NotNull Character altChar) {
        Validator.checkNotNull(text, "text cannot be null");
        Validator.checkNotNull(altChar, "altChar cannot be null");

        return ChatColor.translateAlternateColorCodes(altChar, text);
    }

    public static List<String> getColoredTexts(@NotNull List<String> texts) {
        Validator.checkNotNull(texts, "texts cannot be null");

        return texts.stream().map(Texts::getColoredText).collect(Collectors.toList());
    }

    public static List<String> getColoredTexts(@NotNull List<String> texts, @NotNull Character altChar) {
        Validator.checkNotNull(texts, "texts cannot be null");
        Validator.checkState(!texts.contains(null), "texts cannot contains null");
        Validator.checkNotNull(altChar, "altChar cannot be null");

        return texts.stream().map(s -> getColoredText(s, altChar)).collect(Collectors.toList());
    }

    public static List<String> setPlaceholders(@NotNull List<String> texts, @NotNull PlaceholderHandler placeholderHandler) {
        Validator.checkNotNull(texts, "texts cannot be null");
        Validator.checkState(!texts.contains(null), "texts cannot contains null");
        Validator.checkNotNull(placeholderHandler, "placeholderHandler cannot be null");

        return texts.stream().map(it -> setPlaceholders(it, placeholderHandler)).collect(Collectors.toList());
    }

    public static String setPlaceholders(@NotNull String text, @NotNull PlaceholderHandler placeholderHandler) {
        Validator.checkNotNull(text, "text cannot be null");
        Validator.checkNotNull(placeholderHandler, "placeholderHandler cannot be null");

        int len = text.length();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char currentChar = text.charAt(i);

            if (currentChar == '$') {
                int nextIndex = i + 1;

                if (nextIndex == len) {
                    result.append("$");
                    break;
                }

                char nextChar = text.charAt(nextIndex);

                switch (nextChar) {
                    case '{': // 占位符
                        int right = text.indexOf("}", nextIndex);

                        if (right != -1) {
                            String placeholder = text.substring(nextIndex + 1, right);
                            String value = placeholderHandler.getValue(placeholder);

                            if (value != null) {
                                result.append(value);
                                i = right;
                                break;
                            } else {
                                result.append("$");
                            }
                        } else { // 找不到 }
                            result.append("$");
                            break;
                        }

                        break;
                    case '$': // 转义
                        result.append("$");
                        i++;
                        break;
                    default: // 无效匹配
                        result.append("$");
                        break;
                }
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}
