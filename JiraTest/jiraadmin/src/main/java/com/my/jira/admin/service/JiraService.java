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

    @RequestMapping(value = "deleteUser", method = RequestMethod.DELETE,
            headers = {"Content-type=application/json"})
    public ResponseEntity<String> deleteUser(@RequestParam Integer userId, @RequestHeader HttpHeaders authorizationHeader);
}
