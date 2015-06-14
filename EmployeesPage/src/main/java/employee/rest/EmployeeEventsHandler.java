/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import db.functions.DBFunctions;
import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author tigran
 */
@Controller
@RequestMapping("/data")
public class EmployeeEventsHandler {

    DBFunctions dbActions = new DBFunctions();

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public @ResponseBody
    String example() {
        return "employee example";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<Employee> loginSystem(@RequestHeader HttpHeaders headers,
            @RequestBody Employee receivedData) throws IOException {
        String userName = receivedData.getUserName();
        String password = receivedData.getPassword();
        System.out.println("userName========" + userName);
        System.out.println("password========" + password);
        Employee employee = dbActions.loginToSystem(userName, password);
        
        //
// do various things, perhaps:
        System.out.println("logggged ddddaaaataa=========" + employee.toString());
        if (null == employee) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        Employee response = mapper.readValue(employee.toString(), Employee.class);
        return new ResponseEntity<Employee>(response, HttpStatus.OK);
    }
}
