package com.vquick.rest.service;

import com.vquick.dao.data.User;
import com.vquick.dao.persist.CrudFunctions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tigran on 1/31/16.
 */
@Controller
@RequestMapping("/service")
public interface RestService {

    CrudFunctions crudFunctions = new CrudFunctions();

    @RequestMapping(value = "example", method = RequestMethod.GET)
    public ResponseEntity<String> getUserTasks();

    @RequestMapping(value = "createUser", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<User> createUser(@RequestBody User user/*, @RequestHeader HttpHeaders authorizationHeader*/);
}
