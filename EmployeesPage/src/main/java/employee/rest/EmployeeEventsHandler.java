/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import db.functions.AccessTokens;
import db.functions.DBFunctions;
import edit.data.EditPassword;
import java.io.IOException;
import java.math.BigInteger;
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
import org.apache.commons.lang.RandomStringUtils;
import java.security.SecureRandom;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tigran
 */
@Controller
@RequestMapping("/data")
public class EmployeeEventsHandler {

    DBFunctions dbActions = new DBFunctions();
    AccessTokens accessTokens = AccessTokens.getInstance();
    final String symbols = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ?><()*&%$#@!`~";
    RandomStringUtils accessTokenGenerator = new RandomStringUtils();

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public @ResponseBody
    String example() {
        return "employee example";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<Employee> loginSystem(@RequestHeader HttpHeaders headers,
            @RequestBody Employee receivedData) throws IOException {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String userName = receivedData.getUserName();
        String password = receivedData.getPassword();
        System.out.println("userName========" + userName);
        System.out.println("password========" + password);
        Employee employee = dbActions.loginToSystem(userName, password);
        if (null == employee) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        Employee response = mapper.readValue(employee.toString(), Employee.class);
        accessTokens.setAccessToken(userName, accessTokenGenerator.random(150, symbols));
        response.setAccessToken(accessTokens.getEmployeeAccessToken(userName));
        System.out.println("accessToken========" + accessTokens.getEmployeeAccessToken(userName));
        return new ResponseEntity<Employee>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<JSONObject> editPassword(@RequestHeader HttpHeaders headers,
            @RequestBody EditPassword passData) throws JSONException {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("AccessToken =======" + accessToken);
        System.out.println("userName =============" + userName);
        if (dbActions.updateEmployeePassword(userName, passData.getOldPassword(),
                passData.getNewPassword())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
         //System.out.println("newPass========" + newPassword);

    }

}
