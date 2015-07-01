/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

import db.functions.AccessTokens;
import db.functions.DBFunctions;
import db.functions.ParserFunctions;
import edit.data.EditPassword;
import java.io.IOException;
import java.util.List;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import my.email.Inbox;
import my.email.Sent;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tigran
 */
@Controller
@RequestMapping("/data")
public class EmployeeEventsHandler {

    DBFunctions dbActions = new DBFunctions();
    ParserFunctions parser = ParserFunctions.getInstance();
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
            @RequestBody Employee receivedData, HttpServletRequest request) throws IOException {
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
        HttpSession session = request.getSession(true);
        System.out.println("accessToken========" + accessTokens.getEmployeeAccessToken(userName));
        accessTokens.printAccessTokens();
        session.setAttribute("userName", userName);
        session.setAttribute("password", password);
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

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST,
            headers = {"Content-type=application/json"})
    public ResponseEntity<String> sendMessage(@RequestHeader HttpHeaders headers,
            @RequestBody Sent messageData) {
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
        if (dbActions.sendMessage(userName, messageData)) {
            System.out.println("The message has been sent successfully.");
            return new ResponseEntity<>("{\"ResponseCode\":200, \"RequestStatus\":"
                    + "\"The message has been sent successfully\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"ResponseCode\":400, \"RequestStatus\":"
                + "\"Failed to send email\"}", HttpStatus.BAD_REQUEST);
    }

    /*@RequestMapping(value = "getinbox", method = RequestMethod.GET)
     public ResponseEntity<String> getInbox(@RequestHeader HttpHeaders headers) {
     if (!headers.containsKey("Authorization")
     || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
     || !headers.containsKey("access-token")
     || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
     return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
     }
     String accessToken = headers.get("access-token").get(0);
     String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
     System.out.println("userName============" + userName);
     String inboxList = dbActions.getInbox(userName);
     System.out.println("Inboxes==========" + inboxList);
     return new ResponseEntity<>(inboxList, HttpStatus.OK);
     }*/
    @RequestMapping(value = "getinbox", method = RequestMethod.GET)
    public ResponseEntity<List<Inbox>> getInbox(@RequestHeader HttpHeaders headers) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        List<Inbox> inboxList = dbActions.getInbox(userName);
        System.out.println("Inboxes==========" + inboxList);
        return new ResponseEntity<>(inboxList, HttpStatus.OK);
    }

    @RequestMapping(value = "getsent", method = RequestMethod.GET)
    public ResponseEntity<List<Sent>> getSent(@RequestHeader HttpHeaders headers) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        List<Sent> sentBoxList = dbActions.getSentBox(userName);
        System.out.println("SentBoxes==========" + sentBoxList);
        return new ResponseEntity<>(sentBoxList, HttpStatus.OK);
    }

    @RequestMapping(value = "getsenttable", method = RequestMethod.GET)
    public ResponseEntity<String> getSentTable(@RequestHeader HttpHeaders headers) {
        ResponseEntity<List<Sent>> getSentResponse = getSent(headers);
        if (getSentResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Sent> sentMessages = getSentResponse.getBody();
        String sentTable = parser.parseSentboxToTable(sentMessages);
        return new ResponseEntity<>(sentTable, HttpStatus.OK);
    }

    @RequestMapping(value = "getinboxtable", method = RequestMethod.GET)
    public ResponseEntity<String> getInboxTable(@RequestHeader HttpHeaders headers) {
        ResponseEntity<List<Inbox>> getInboxResponse = getInbox(headers);
        if (getInboxResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Inbox> sentMessages = getInboxResponse.getBody();
        String inboxTable = parser.parseInboxToTable(sentMessages);
        return new ResponseEntity<>(inboxTable, HttpStatus.OK);

    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity<String> logOutEmployee(@RequestHeader HttpHeaders headers) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        AccessTokens.getInstance().deletAccessToken(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "getaninbox", method = RequestMethod.GET)
    public ResponseEntity<String> getAnInboxMessage(@RequestHeader HttpHeaders headers,
            @RequestParam("id") int id) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        System.out.println("id==========" + id);
        //Integer intId = Integer.getInteger(id);
        Inbox inboxMessage = dbActions.getInboxMessageByID(userName, id);
        parser.setInboxMessag(inboxMessage);
        if (null == inboxMessage) {
            return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"Failed to get  the requested message!\"}",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(inboxMessage.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "getanhtmlinbox", method = RequestMethod.GET)
    public ResponseEntity<String> getAnHTMLInboxMessage(@RequestHeader HttpHeaders headers,
            @RequestParam("id") int id) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        System.out.println("id==========" + id);
        //Integer intId = Integer.getInteger(id);
        Inbox inboxMessage = dbActions.getInboxMessageByID(userName, id);
        parser.setInboxMessag(inboxMessage);
        if (null == inboxMessage) {
            return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"Failed to get  the requested message!\"}",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(parser.parseAnInboxMessage(), HttpStatus.OK);
    }

    @RequestMapping(value = "getasent", method = RequestMethod.GET)
    public ResponseEntity<String> getASentMessage(@RequestHeader HttpHeaders headers,
            @RequestParam("id") int id) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        System.out.println("id==========" + id);
        //Integer intId = Integer.getInteger(id);
        Sent sentMessage = dbActions.getSentMessageByID(userName, id);
        parser.setSentMessage(sentMessage);
        if (null == sentMessage) {
            return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"Failed to get  the requested message!\"}",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sentMessage.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "getahtmlsent", method = RequestMethod.GET)
    public ResponseEntity<String> getAHTMLSentMessage(@RequestHeader HttpHeaders headers,
            @RequestParam("id") int id) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        System.out.println("id==========" + id);
        //Integer intId = Integer.getInteger(id);
        Sent sentMessage = dbActions.getSentMessageByID(userName, id);
        parser.setSentMessage(sentMessage);
        if (null == sentMessage) {
            return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"Failed to get  the requested message!\"}",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(parser.parseASendMessage(), HttpStatus.OK);
    }

    @RequestMapping(value = "deleteaninbox", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAnInboxMessage(@RequestHeader HttpHeaders headers,
            @RequestParam("id") int id) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        System.out.println("id==========" + id);
        //Integer intId = Integer.getInteger(id);
        if(dbActions.removeSpecifiedInboxMessage(userName, id)){
            return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"The message is removed successfully!\"}",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"Failed to remove the specified message!\"}", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "deleteasent", method = RequestMethod.POST)
    public ResponseEntity<String> deleteASentMessage(@RequestHeader HttpHeaders headers,
            @RequestParam("id") int id) {
        if (!headers.containsKey("Authorization")
                || !headers.get("Authorization").get(0).equals("FD aw4567123edkk99")
                || !headers.containsKey("access-token")
                || null == accessTokens.getEmployeeUsernameByAccessToken(headers.get("access-token").get(0))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String accessToken = headers.get("access-token").get(0);
        String userName = accessTokens.getEmployeeUsernameByAccessToken(accessToken);
        System.out.println("userName============" + userName);
        System.out.println("id==========" + id);
        //Integer intId = Integer.getInteger(id);
        if(dbActions.removeSpecifiedSentMessage(userName, id)){
            return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"The message is removed successfully!\"}",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"ResponseBody\":"
                    + "\"Failed to remove the specified message!\"}", HttpStatus.BAD_REQUEST);
    }

}
