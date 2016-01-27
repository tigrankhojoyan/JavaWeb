package com.my.jira.admin.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tigrank on 1/27/2016.
 */

@Controller
@RequestMapping("/testService")
public class TestService {

    @RequestMapping(value = "exceptionHandlerTest/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> exceptionHandlerTest(@PathVariable String id) {
        if(id.equals("2")) {
            throw new RuntimeException("Illegal exception thrown");
        }

        return new ResponseEntity<String>("Normal response", HttpStatus.OK);
    }
}
