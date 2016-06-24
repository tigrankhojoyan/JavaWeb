package com.vquick.rest.service;

import com.vquick.dao.data.User;
import com.vquick.dao.persist.impl.UserDaoServiceImpl;
import com.vquick.rest.service.data.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by tigran on 1/31/16.
 */
@Controller
@RequestMapping("/service")
public interface RestService {

    @RequestMapping(value = "example", method = RequestMethod.GET)
    ResponseEntity<String> getUserTasks();

    @RequestMapping(value = "createUser", method = RequestMethod.POST,
            headers = {"Content-type=application/json"}, produces = "application/json; charset=UTF-8")
    ResponseEntity<User> createUser(@RequestBody User user/*, @RequestHeader HttpHeaders authorizationHeader*/);

    @RequestMapping(value = "deleteUser", method = RequestMethod.DELETE,
            headers = {"Content-type=application/json"}, produces = "application/json; charset=UTF-8")
    ResponseEntity<Response> deleteUser(@RequestParam(required = true, value = "userName") String userNamer/*, @RequestHeader HttpHeaders authorizationHeader*/);
}
