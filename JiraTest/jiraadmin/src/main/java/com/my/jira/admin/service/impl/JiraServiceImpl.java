package com.my.jira.admin.service.impl;

import com.my.jira.admin.data.Task;
import com.my.jira.admin.data.User;
import com.my.jira.admin.data.dao.CrudFunctions;
import com.my.jira.admin.service.JiraService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by tigran on 1/18/16.
 */
@Controller
@RequestMapping("/jiraService")
public class JiraServiceImpl implements JiraService {

    CrudFunctions crudFunctions = new CrudFunctions();
    ResourceBundle resourceBundle = ResourceBundle.getBundle("keys");

    @Override
    public ResponseEntity<String> createUser(@RequestBody User user, @RequestHeader HttpHeaders authorizationHeader) {
        // Checks if headers are correct
        if(!authorizationHeader.containsKey("authorization") ||
                !authorizationHeader.get("authorization").get(0).equals(resourceBundle.getString("authorizationAddUser"))) {
            return new ResponseEntity<String>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }

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
        ArrayList<Task> userTasks = new ArrayList<Task>();
        userTasks = crudFunctions.getTasksOfSpecifiedUser(userId);
        if(userTasks.size() == 0) {
            return new ResponseEntity<ArrayList<Task>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<ArrayList<Task>>(userTasks, HttpStatus.OK);
    }

    /*@Override
    public ResponseEntity<String> assignTask(@RequestParam Integer taskId, @RequestParam Integer userId, HttpHeaders authorizationHeader) {
        return null;
    }*/

    @Override
    public ResponseEntity<String> updateTaskStatus(@RequestParam Integer taskId, @RequestParam String taskStatus, @RequestHeader HttpHeaders authorizationHeader) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteUser(@RequestParam Integer userId, @RequestHeader HttpHeaders authorizationHeader) {
        return null;
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String example() {
        return "example";
    }
}
