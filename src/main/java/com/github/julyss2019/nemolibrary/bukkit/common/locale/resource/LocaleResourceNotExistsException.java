package com.github.julyss2019.nemolibrary.bukkit.common.locale.resource;

public class LocaleResourceNotExistsException extends RuntimeException {
    public LocaleResourceNotExistsException(String message) {
        super(String.format("locale '%s' not exists", message));
    }
}
