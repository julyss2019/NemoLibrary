package com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.layout.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.logging.logger.layout.Layout;
import com.github.julyss2019.nemolibrary.bukkit.common.logging.MessageContext;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PatternLayout implements Layout {
    private final String pattern;

    public PatternLayout(@NotNull String pattern) {
        Validator.checkNotNull(pattern, "pattern cannot be null");

        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    @Override
    public String format(MessageContext context) {
        Validator.checkNotNull(context, "msg cannot be null");


        StringBuilder result = new StringBuilder();
        int len = pattern.length();

        for (int i = 0; i < len; i++) {
            int currentIndex = i;
            char current = pattern.charAt(currentIndex);

            if (current != '%') {
                result.append(current);
                continue;
            }

            int typeIndex = currentIndex + 1;

            if (typeIndex == len) {
                result.append("%");
                break;
            }

            char type = pattern.charAt(typeIndex); // 戴解析的参数

            i++; // 加上 typeIndex

            switch (type) {
                case 'd':
                    int begin = typeIndex + 1;
                    int left = pattern.indexOf("{", begin);
                    int right = pattern.indexOf("}", begin);

                    if (left == -1 || right == -1) {
                        throw new RuntimeException("invalid date format in position: " + begin);
                    }

                    String dateFormat = pattern.substring(left + 1, right);

                    result.append(new SimpleDateFormat(dateFormat).format(new Date()));
                    i = right;
                    continue;
                case 'c':
                    result.append("§").append(context.getColor().getChar());
                    break;
                case 'p':
                    result.append(context.getPlugin().getName());
                    break;
                case 'm':
                    result.append(context.getMsg());
                    break;
                case 'l':
                    result.append(context.getLevel().name());
                    break;
                default:
                    throw new RuntimeException("invalid argument type: " + type);
            }
        }

        return result.toString();
    }
}
