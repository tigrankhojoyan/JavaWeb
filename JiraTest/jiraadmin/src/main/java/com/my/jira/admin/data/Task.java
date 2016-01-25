package com.my.jira.admin.data;

/**
 * Created by tigran on 1/18/16.
 */
public class Task {

    private int taskId;
    private String taskDescription;
    private String taskStatus;
    private String reporter;
    private int assigneeUserId;

    public Task(String taskDescription, String reporter, int assigneeUserId) {
        this.taskDescription = taskDescription;
        this.taskStatus = TaskStatuses.OPEN.getTaskStatus();
        this.reporter = reporter;
        this.assigneeUserId = assigneeUserId;
    }

    public Task(){

    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public int getAssigneeUserId() {
        return assigneeUserId;
    }

    public void setAssigneeUserId(int assigneeUserId) {
        this.assigneeUserId = assigneeUserId;
    }

    @Override
    public String toString() {
        return "{\"" +
                "taskId\":" + taskId +
                ", \"taskDescription\":\"" + taskDescription + '\"' +
                ", \"taskStatus\":\"" + taskStatus + '\"' +
                ", \"reporter\":\"" + reporter + '\"' +
                ", \"assigneeUserId\":" + assigneeUserId +
                '}';
    }
}
