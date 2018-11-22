package com.sayeed.truktotasks.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String taskDescription;
    private boolean expanded;

    public Task(@NonNull String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @NonNull
    public String getTaskDescription() {
        return this.taskDescription;
    }

    void setTaskDescription(@NonNull String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}