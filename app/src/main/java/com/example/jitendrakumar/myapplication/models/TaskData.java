package com.example.jitendrakumar.myapplication.models;

public class TaskData {
    private String task;

    public TaskData()
    {

    }

    public TaskData(String task, String taskId, String taskDate) {
        this.task = task;
        this.taskId = taskId;
        this.taskDate = taskDate;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    private String taskId;
    private String taskDate;
}
