package com.github.julyss2019.nemolibrary.bukkit.common.task.async;

public class TaskLock {
    private boolean locked;

    public void checkLocked() {
        if (isLocked()) {
            throw new RuntimeException("task is waiting");
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void lock() {
        setLocked(true);
    }

    public void unlock() {
        setLocked(false);
    }
}
