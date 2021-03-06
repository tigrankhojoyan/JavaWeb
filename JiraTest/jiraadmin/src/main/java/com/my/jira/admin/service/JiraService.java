package com.my.jira.admin.service;

import com.my.jira.admin.data.Task;
import com.my.jira.admin.data.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigran on 1/18/16.
 */
@Controller
@RequestMapping("/jiraService")
public interface JiraService {

    @RequestMapping(value = "createUser", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<String> createUser(@RequestBody User user, @RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "createTask", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<String> createTask(@RequestBody Task task, @RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "getUserTask", method = RequestMethod.GET,
            headers = {"Content-type=application/json"})
    public ResponseEntity<ArrayList<Task>> getUserTasks(@RequestParam Integer userId, @RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "assignTask", method = RequestMethod.PUT,
            headers = {"Content-type=application/json"})
    public ResponseEntity<String> assignTask(@RequestParam(value = "taskId") Integer taskId, @RequestParam(value = "userName") String userName, @RequestParam(value = "userId") Integer userId, @RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "updateTaskStatus", method = RequestMethod.PUT,
            headers = {"Content-type=application/json"})
    public ResponseEntity<String> updateTaskStatus(@RequestParam Integer taskId, @RequestParam String taskStatus, @RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "deleteUser/{userName}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable String userName, @RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "deleteTask/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTask(@PathVariable Integer taskId, @RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "exceptionHandlerTest/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> exceptionHandlerTest(@PathVariable String id);

    @RequestMapping(value = "getAllTask", method = RequestMethod.GET,
            headers = {"Content-type=application/json"})
    public ResponseEntity<ArrayList<Task>> getAllTasks(@RequestHeader HttpHeaders authorizationHeader);

    @RequestMapping(value = "getAllUsers", method = RequestMethod.GET,
            headers = {"Content-type=application/json"})
    public ResponseEntity<ArrayList<User>> getAllUsers(@RequestHeader HttpHeaders authorizationHeader);
}
