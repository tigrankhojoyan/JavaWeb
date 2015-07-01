/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.rest;

import db.functions.AccessTokens;
import db.functions.DBFunctions;
import employee.rest.Employee;
import java.util.List;
import java.util.UUID;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tigran
 */
@Controller
@RequestMapping("/data")
public class AdminEventsHandler {

    DBFunctions dbActions = new DBFunctions();

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public @ResponseBody
    String example() {
        AccessTokens accessTokens = AccessTokens.getInstance();
        accessTokens.printAccessTokens();
        return "example";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<Employee> addEmployeeData(@RequestBody Employee receivedData,
            @RequestHeader HttpHeaders header) {
        if (header.containsKey("Authorization") && header.get("Authorization").get(0).equals("FD aw4567123edkk99")) {
            String swid = "{" + UUID.randomUUID().toString() + "}";
            dbActions.addEmployee(receivedData.getFullName(), receivedData.getAddress(),
                    receivedData.getSalary(), receivedData.getUserName(),
                    receivedData.getPassword(), receivedData.getEmployeeData(), swid);
            receivedData.setSwid(swid);
            return new ResponseEntity<Employee>(receivedData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/employees/getAllData", method = RequestMethod.GET,
            headers = {"Content-type=application/json"})
    public ResponseEntity<List<Employee>> getAllEmployees(
            @RequestHeader HttpHeaders header) {
        if (header.containsKey("Authorization") && header.get("Authorization").get(0).equals("FD aw4567123edkk99")) {
            List<Employee> employeesList = dbActions.getAllEmployees();
            return new ResponseEntity<>(employeesList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/employees/deleteData", method = RequestMethod.DELETE,
            headers = {"Content-type=application/json"})
    public ResponseEntity<JSONPObject> deleteEmployee(@RequestHeader HttpHeaders header,
            @RequestParam(value = "userName") String userName) {
        JSONPObject responseBody = new JSONPObject("deletionStatus", "The '" + userName
                + "' employee data has been removed successfully.");
        if (header.containsKey("Authorization") && header.get("Authorization").get(0).equals("FD aw4567123edkk99")) {
            if (dbActions.deleteEmployeeData(userName)) {
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }
            responseBody = new JSONPObject("deletionStatus",
                    "Failed to delete '" + userName + "' employee data.");
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/employees/updateEmployee", method = RequestMethod.PUT,
            headers = {"Content-type=application/json"}, produces = "application/json")
    public ResponseEntity<String> updateEmployee(@RequestHeader HttpHeaders headers,
            @RequestBody Employee receivedData) throws JSONException {
        System.out.println("uuuuuuuuuuuuuoooooooooooooooooooooooooooo"
                + ""
                + "");
        System.out.println(receivedData.getSalary());
        System.out.println(receivedData.getFullName());
        //return new ResponseEntity<>(receivedData, HttpStatus.OK);
        JSONObject response = new JSONObject();
        String fullName, address;
        int salary;
        // Validates the request body
        fullName = receivedData.getFullName();
        address = receivedData.getAddress();
        salary = receivedData.getSalary();
        if (null == fullName || address == null || salary <= 0) {
            response.put("Responsecode", "400");
            response.put("UpdateStatus", "Failed to update salary. "
                    + "The request body is incorrect.");
            return new ResponseEntity<>(response.toString(), HttpStatus.BAD_REQUEST);
        }
        // Tries to update data for the specified employee
        if (headers.containsKey("Authorization") && headers.get("Authorization").get(0).equals("FD aw4567123edkk99")) {
            if (dbActions.updateEmployeeSalary(fullName, address, salary)) {
                response.put("Responsecode", "200");
                response.put("UpdateStatus", "The salary for '" + fullName
                        + "' employee has been updated successfully");
                return new ResponseEntity<>(response.toString(), HttpStatus.ACCEPTED);
            } else {
                response.put("Responsecode", "403");
                response.put("UpdateStatus", "Failed to update salary.");
                return new ResponseEntity<>(response.toString(), HttpStatus.FORBIDDEN);
            }
        } else {
            response.put("Responsecode", "401");
            response.put("UpdateStatus", "Authorization needed to update data.");
            return new ResponseEntity<>(response.toString(), HttpStatus.UNAUTHORIZED);
        }
    }
}
