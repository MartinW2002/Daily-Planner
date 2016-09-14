package me.martinwiesner.dailyplanner;

import java.io.Serializable;

/**
 * Created by Martin on 14-Sep-16.
 */
public class Task implements Serializable {

    private static final long serialVersionUID = 4165854565L;

    private String task;
    private boolean completed;

    public Task(String task, boolean completed) {
        this.task = task;
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
