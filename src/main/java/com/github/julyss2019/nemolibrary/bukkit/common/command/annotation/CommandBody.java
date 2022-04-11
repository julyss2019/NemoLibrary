package com.github.julyss2019.nemolibrary.bukkit.common.command.annotation;



import com.github.julyss2019.nemolibrary.bukkit.common.command.SenderType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命令体
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandBody {
    /**
     * 命名名
     * @return
     */
    String name();

    /**
     * 别名
     * @return
     */
    String[] aliases() default {};

    /**
     * 参数描述
     * 支持使用占位符
     * @return
     */
    String argumentsDescription();


    /**
     * 命令描述
     * 支持使用占位符
     * @return
     */
    String description();

    /**
     * 权限
     * @return 默认无权限
     */
    String permission();

    /**
     * 发送者
     * @return 默认允许包含 PLAYER 和 CONSOLE
     */
    SenderType[] targetSenders() default {SenderType.PLAYER, SenderType.CONSOLE};
}
