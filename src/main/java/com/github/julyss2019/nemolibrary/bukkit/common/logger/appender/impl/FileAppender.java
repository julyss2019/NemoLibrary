package com.github.julyss2019.nemolibrary.bukkit.common.logger.appender.impl;

import com.github.julyss2019.nemolibrary.bukkit.common.logger.Level;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.MessageContext;
import com.github.julyss2019.nemolibrary.bukkit.common.logger.layout.Layout;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.Validator;
import com.github.julyss2019.nemolibrary.bukkit.common.validation.annotation.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.FileTime;

public class FileAppender extends BaseAppender {
    private File file;
    private BufferedWriter bufferedWriter;
    private int flushInterval;

    public FileAppender(@NotNull Layout layout, @NotNull Level threshold, @NotNull File file, int flushInterval) {
        super(layout, threshold);

        setFile(file);
        setFlushInterval(flushInterval);
    }

    public int getFlushInterval() {
        return flushInterval;
    }

    public void setFlushInterval(int flushInterval) {
        Validator.checkState(flushInterval >= 0, "flushInterval must >= 0");

        this.flushInterval = flushInterval;
    }

    public void setFile(@NotNull File file) {
        Validator.checkNotNull(file, "file cannot be null");

        if (this.file == null || !this.file.equals(file)) {
            closeWriter();
            this.file = file;
        }
    }

    public File getFile() {
        return file;
    }

    /**
     * 文件重命名
     * @param dest
     */
    protected void renameTo(@NotNull File dest) {
        Validator.checkNotNull(dest, "dest cannot be null");

        closeWriter();

        if (!this.file.renameTo(dest)) {
            throw new RuntimeException("rename file " + file.getAbsolutePath() + " to " + dest.getAbsolutePath() + " failed");
        }
    }

    /**
     * 关闭写入器
     */
    private void closeWriter() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.flush();
                bufferedWriter.close();
                bufferedWriter = null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 打开写入器
     */
    private void setNewWriter() {
        try {
            File parent = file.getParentFile();

            if (!parent.exists() && !parent.mkdirs()) {
                throw new RuntimeException("mkdirs failed: " + parent.getAbsolutePath());
            }

            // 关于在 Windows 删除文件后再创建，文件创建时间不更新的问题：https://stackoverflow.com/questions/20884521/date-created-is-not-going-to-change-while-delete-file-and-then-create-file
            if (!file.exists() && !file.createNewFile()) {
                throw new RuntimeException("create new file failed: " + file.getAbsolutePath());
            }

            Files.setAttribute(file.toPath(), "creationTime", FileTime.fromMillis(System.currentTimeMillis()), LinkOption.NOFOLLOW_LINKS);

            this.bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flush() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void write(@NotNull String line) {
        Validator.checkNotNull(line, "line cannot be null");

        if (bufferedWriter == null) {
            setNewWriter();
        }

        try {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();

        closeWriter();
    }

    @Override
    public void execute(@NotNull MessageContext messageContext) {
        super.execute(messageContext);

        write(getLayout().format(messageContext));
    }
}
