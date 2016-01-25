package com.my.jira.admin.data;

/**
 * Created by tigran on 1/18/16.
 */
public enum TaskStatuses {

    OPEN("open"),
    RESOLVED("resolved"),
    CLOSE("close"),
    RE_OPENED("reOpened"),
    IN_PROGRESS("inProgress");


    private String taskStatus;

    private TaskStatuses(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskStatus(){
        return taskStatus;
    }
}
