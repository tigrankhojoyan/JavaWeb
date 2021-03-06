package com.my.jira.admin.service.impl;

import com.my.jira.admin.data.Task;
import com.my.jira.admin.data.User;
import com.my.jira.admin.data.dao.CrudFunctions;
import com.my.jira.admin.data.dao.CrudStatuses;
import com.my.jira.admin.service.JiraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by tigran on 1/18/16.
 */
@Controller
@RequestMapping("/jiraService")
public class JiraServiceImpl implements JiraService {

    CrudFunctions crudFunctions = new CrudFunctions();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("keys");
    Logger logger = LoggerFactory.getLogger(JiraServiceImpl.class);

    @Override
    public ResponseEntity<String> createUser(@RequestBody User user, @RequestHeader HttpHeaders authorizationHeader) {
        // Checks if headers are correct
        if(!authorizationHeader.containsKey("authorization") ||
                !authorizationHeader.get("authorization").get(0).equals(resourceBundle.getString("authorizationAddUser"))) {
            return new ResponseEntity<String>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }

        //Checks if there is another user with specified username
        if(CrudFunctions.isSpecifiedUserExist(user.getUserName()))
            return new ResponseEntity<String>("Duplicate user data", HttpStatus.CONFLICT);

        // Tries to persist given user data and returns appropriate result
        int userInsertionStatus = CrudFunctions.writeUserToDb(user);
        if(userInsertionStatus > -1) {
            return new ResponseEntity<String>("New user has been added successfully",
                    HttpStatus.OK);
        }
        return new ResponseEntity<String>("Failed to add given user",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> createTask(@RequestBody Task task, @RequestHeader HttpHeaders authorizationHeader) {
        if(!authorizationHeader.containsKey("authorization") ||
                !authorizationHeader.get("authorization").get(0).equals(resourceBundle.getString("authorizationAddTask"))) {
            return new ResponseEntity<String>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }
        int taskCreationStatus = crudFunctions.writeTaskToDb(task);

        if(taskCreationStatus > -1) {
            return new ResponseEntity<String>("The new \"" +  task +
                    " \" task has been created successfully.", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<String>("Failed to create new task.", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<ArrayList<Task>> getUserTasks(@RequestParam Integer userId, @RequestHeader HttpHeaders authorizationHeader) {

        ResponseEntity<ArrayList<Task>> authorizationStatus = checkRequestHeaders(authorizationHeader,
                resourceBundle.getString("authorizationAddTask"));
        if(authorizationStatus != null)
            return authorizationStatus;

        ArrayList<Task> userTasks = new ArrayList<Task>();
        userTasks = crudFunctions.getTasksOfSpecifiedUser(userId);
        if(userTasks.size() == 0) {
            return new ResponseEntity<ArrayList<Task>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ArrayList<Task>>(userTasks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> assignTask(@RequestParam(value = "taskId", required = true) Integer taskId, @RequestParam(value = "userName", required = true) String userName,
                                             @RequestParam(value = "userId", required = true) Integer userId, @RequestHeader HttpHeaders authorizationHeader) {

        ResponseEntity<String> authorizationStatus = checkRequestHeaders(authorizationHeader,
                resourceBundle.getString("authorizationUpdateData"));
        if(authorizationStatus != null)
            return authorizationStatus;

        try {
            CrudFunctions.assignTaskToUser(taskId, userName, userId);
            logger.info("The task by [{}] id has been assigned to [{}] user successfully.",
                    new Object[] {taskId, userName});

            return new ResponseEntity<String>("The task by '" + taskId + "' id has been assigned to '" +
                     userName + "' user successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());

            return new ResponseEntity<String>("Failed to assign the task by '" + taskId + "' id to '" +
                    userName + "' user.", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> updateTaskStatus(@RequestParam Integer taskId, @RequestParam String taskStatus, @RequestHeader HttpHeaders authorizationHeader) {
        ResponseEntity<String> authorizationStatus = checkRequestHeaders(authorizationHeader,
                resourceBundle.getString("authorizationUpdateData"));
        if(authorizationStatus != null)
            return authorizationStatus;

        CrudStatuses taskUpdateStatus = CrudFunctions.updateTaskStatus(taskId, taskStatus);
        if(!taskUpdateStatus.equals(CrudStatuses.TASK_STATUS_UPDATE_SUCCESS)) {
            return new ResponseEntity<String>(taskUpdateStatus.getCrudStatusMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(taskUpdateStatus.getCrudStatusMessage(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteUser(@PathVariable String userName, @RequestHeader HttpHeaders authorizationHeader) {
        ResponseEntity<String> authorizationStatus = checkRequestHeaders(authorizationHeader,
                resourceBundle.getString("authorizationDeleteUser"));
        if(authorizationStatus != null)
            return authorizationStatus;

        CrudStatuses userDeleteStatus = CrudFunctions.deleteSpecifiedUser(userName);
        if(userDeleteStatus == CrudStatuses.USER_DELETE_NON_EXISTING_USER)
            return new ResponseEntity<String>(userDeleteStatus.getCrudStatusMessage(), HttpStatus.NOT_FOUND);
        else if(userDeleteStatus == CrudStatuses.USER_DELETE_FAIL)
            return new ResponseEntity<String>(userDeleteStatus.getCrudStatusMessage(), HttpStatus.FORBIDDEN);

        return new ResponseEntity<String>(userDeleteStatus.getCrudStatusMessage(), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId, @RequestHeader HttpHeaders authorizationHeader) {
        ResponseEntity<String> authorizationStatus = checkRequestHeaders(authorizationHeader,
                resourceBundle.getString("authorizationDeleteTask"));
        if(authorizationStatus != null)
            return authorizationStatus;
        CrudStatuses taskDeleteStatus = CrudFunctions.deleteSpecifiedTask(taskId);
        if(taskDeleteStatus == CrudStatuses.TASK_DELETE_NON_EXISTING_TASK)
            return new ResponseEntity<String>(taskDeleteStatus.getCrudStatusMessage(), HttpStatus.NOT_FOUND);
        else if(taskDeleteStatus == CrudStatuses.TASK_DELETE_FAIL)
            return new ResponseEntity<String>(taskDeleteStatus.getCrudStatusMessage(), HttpStatus.FORBIDDEN);

        return new ResponseEntity<String>(taskDeleteStatus.getCrudStatusMessage(), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> exceptionHandlerTest(@PathVariable String id) {
        if(id.equals("1")) {
            throw new RuntimeException("Runtime exception");
        }

        return new ResponseEntity<String>("The exception was not thrown", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArrayList<Task>> getAllTasks(@RequestHeader HttpHeaders authorizationHeader) {
        ResponseEntity<ArrayList<Task>> authorizationStatus = checkRequestHeaders(authorizationHeader,
                resourceBundle.getString("adminTasksKey"));
        if(authorizationStatus != null)
            return authorizationStatus;

        ArrayList<Task> allTasks = CrudFunctions.getAllTasks();
        if(allTasks.size() == 0)
            return new ResponseEntity<ArrayList<Task>>(allTasks, HttpStatus.NO_CONTENT);

        return new ResponseEntity<ArrayList<Task>>(allTasks, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArrayList<User>> getAllUsers(@RequestHeader HttpHeaders authorizationHeader) {
        ResponseEntity<ArrayList<User>> authorizationStatus = checkRequestHeaders(authorizationHeader,
                resourceBundle.getString("adminUsersKey"));
        if(authorizationStatus != null)
            return authorizationStatus;

        ArrayList<User> allUsers = CrudFunctions.getAllUsers();
        if(allUsers.size() == 0)
            return new ResponseEntity<ArrayList<User>>(allUsers, HttpStatus.NO_CONTENT);

        return new ResponseEntity<ArrayList<User>>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String example() {
        return "example";
    }

    public <T> ResponseEntity<T> checkRequestHeaders(HttpHeaders authorizationHeader, String authorizationKey) {

        if(!authorizationHeader.containsKey("authorization") ||
                !authorizationHeader.get("authorization").get(0).equals(authorizationKey)) {
            return new ResponseEntity<T>(HttpStatus.UNAUTHORIZED);
        }

        return null;
    }
}
