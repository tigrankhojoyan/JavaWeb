package com.my.jira.admin.service.impl;

import com.beust.testng.TestNG;
import com.my.jira.admin.data.Task;
import com.my.jira.admin.data.User;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.MyHttpClient;

import java.util.HashMap;

/**
 * Created by tigran on 1/22/16.
 */
public class JiraServiceImplTest extends TestNG {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @Test
    public void testCreateUser() throws Exception {

        User user = new User("testUserName", "testPassword", "testFullName");
        String url = "http://localhost:8085/rest/jiraService/createUser";
//        String body = "{\"userName\":\"testUserName\", \"password\":\"testPassword\", \"fullName\":\"testFullName\"}";

        HttpResponse response = MyHttpClient.sendPost(url, user.toString(), "anAuthorizationKey123Admin");

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    }
    //TODO add negative test cases

    @Test
    public void testCreateTask() throws Exception {

        Task testTask = new Task("testTaskDescription", "testReporter", 0);
        String url = "http://localhost:8085/rest/jiraService/createTask";

        HttpResponse response = MyHttpClient.sendPost(url, testTask.toString(), "anAuthorizationKey123Task");

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 202);
    }

    //TODO add negative test cases

    @Test
    public void testGetUserTasks() throws Exception {

        //todo
    }

    @Test
    public void testAssignTask() throws Exception {

        HashMap<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put("taskId", new Integer(1));
        requestParams.put("userName", new String("testUserName"));
        requestParams.put("userId", new Integer(1));
        String url = "http://localhost:8085/rest/jiraService/assignTask";

        HttpResponse response = MyHttpClient.sendPut(url, requestParams, "anAuthorizationKey123Update");
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    }

    @Test
    public void testUpdateTaskStatus() throws Exception {

    }

    @Test
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testExample() throws Exception {

    }
}