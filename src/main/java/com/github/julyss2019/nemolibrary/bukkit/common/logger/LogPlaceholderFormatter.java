package com.github.julyss2019.nemolibrary.bukkit.common.logger;

import com.github.julyss2019.nemolibrary.bukkit.common.util.ArrayUtils;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

public class LogPlaceholderFormatter {
    public static String format(@NotNull String msg, Object... args) {
        Validator.checkNotNull(msg, "msg cannot be null");
        Validator.checkState(!ArrayUtils.containsElement(args, null), "args cannot contains null");

        if (args.length == 0) {
            return msg;
        }

        StringBuilder result = new StringBuilder();
        int len = msg.length();

        // {0} {1} {{0}}
        for (int i = 0; i < len; i++) {
            char current = msg.charAt(i);

            // 非解析字符直接跳过并添加
            if (current != '{') {
                result.append(current);
                continue;
            }

            int nextIndex = i + 1;

            if (nextIndex == len) {
                result.append(current);
                break;
            }

            char next = msg.charAt(nextIndex);

            // 转义，{{
            if (next == '{') {
                result.append("{");

                int tmp = msg.indexOf("}}", nextIndex + 1);

                if (tmp == -1) {
                    result.append("{");
                    i = nextIndex;
                    continue;
                }

                i = tmp;
                result.append(msg, nextIndex + 1, i);
            } else {
                int right = msg.indexOf("}", nextIndex + 1);

                if (right != -1) {
                    int argIndex;

                    // 异常处理
                    try {
                        argIndex = Integer.parseInt(msg.substring(nextIndex, right));
                    } catch (Exception e) {
                        result.append("{");
                        continue;
                    }

                    i = right;
                    result.append(args[argIndex]);
                } else {
                    result.append("{");
                }
            }
        }

        return result.toString();
    }
}
