/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.my.batis;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author tigran
 */
@Controller
@RequestMapping("/user")
public class RestController {
    UserSevice service = new UserSevice();

    @RequestMapping(value = "/registration", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<User> addUser(@RequestBody User receivedData) {
        service.insertUser(receivedData);
        return new ResponseEntity<>(receivedData, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.PUT,
            headers = {"Content-type=application/json"})
    public ResponseEntity<List<User>> updateUser(@RequestBody User receivedData) {
        service.updateUser(receivedData);
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
    
}
