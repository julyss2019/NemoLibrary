package com.github.julyss2019.nemolibrary.bukkit.common.logger.appender.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.logger.Level;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.MessageContext;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.layout.Layout;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RollingFileAppender extends FileAppender {
    private String datePattern;
    private SimpleDateFormat sdf;

    public RollingFileAppender(@NotNull Layout layout, @NotNull Level threshold, @NotNull File file, @NotNull int flushInterval, @NotNull String datePattern) {
        super(layout, threshold, file, flushInterval);

        setDatePattern(datePattern);
        roll();
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(@NotNull String datePattern) {
        Validator.checkNotNull(datePattern, "datePattern cannot be null");

        this.datePattern = datePattern;
        this.sdf = new SimpleDateFormat(datePattern);
    }

    private void roll() {
        File file = getFile();

        // 没被 roll 的文件不存在则跳过
        if (!file.exists()) {
            return;
        }

        BasicFileAttributes fileAttributes;

        try {
            fileAttributes = Files.readAttributes(getFile().toPath(), BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Date nowTime = new Date();
        Date fileCreateTime = new Date(fileAttributes.creationTime().toMillis());
        String newFileName = sdf.format(nowTime);
        String oldFileName = sdf.format(fileCreateTime);

        if (!newFileName.equals(oldFileName)) {
            renameTo(new File(file.getParentFile(), oldFileName));
        }
    }

    @Override
    public void execute(@NotNull MessageContext messageContext) {
        roll();
        super.execute(messageContext);
    }
}
