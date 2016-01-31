package com.vquick.rest.service.impl;

import com.vquick.dao.data.User;
import com.vquick.rest.service.RestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tigran on 1/31/16.
 */
@Controller
@RequestMapping("/service")
public class RestServiceImpl implements RestService{

    @Override
    public ResponseEntity<String> getUserTasks() {
        return new ResponseEntity<String>("Request Succeed", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> createUser(@RequestBody User user) {

        user = crudFunctions.addUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);

    }

}
