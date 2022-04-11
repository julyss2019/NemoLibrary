package com.github.julyss2019.nemolibrary.bukkit.common.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 命令体前面的部分，用作标识符，统一权限管理
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandBefore {
    /**
     * 命令
     * @return
     */
    String name();

    /**
     * 别名
     * @return
     */
    String[] aliases() default {};

    String permission() default "";
}
