package com.my.jira.admin.data.dao;

/**
 * Created by tigrank on 1/26/2016.
 */
public enum CrudStatuses {

    USER_CREATION_SUCCESS("userCreated", "User created successfully."),
    TASK_CREATION_STATUS("taskCreated", "The task has been created successfully."),
    USER_DELETE_SUCCESS("userDeleted", "The user has been deleted successfully."),
    TASK_DELETE_SUCCESS("taskDeleted", "The task has been deleted successfully."),
    TASK_STATUS_UPDATE_INVALID("invalidTaskStatus", "The given status is invalid."),
    TASK_STATUS_UPDATE_FAILED("taskUpdateFail", "Failed to update status for the task."),
    TASK_STATUS_UPDATE_SUCCESS("taskUpdateSuccess", "The task has been updated successfully."),
    TASK_ASSIGN_STATUS_SUCCESS("taskAssignSuccess", "The task has been assigned to another user."),
    TASK_ASSIGN_STATUS_FAIL("taskAssignFail", "Failed to assign task to another user.");


    private String crudStatus;
    private String crudStatusMessage;

    private CrudStatuses(String crudStatus, String crudStatusMessage) {
        this.crudStatus = crudStatus;
        this.crudStatusMessage = crudStatusMessage;
    }

    public String getCrudStatus() {
        return crudStatus;
    }

    public String getCrudStatusMessage() {
        return crudStatusMessage;
    }
}
