package com.vquick.rest.service.impl;

import com.vquick.business.exception.BusinessException;
import com.vquick.business.service.UserManagerService;
import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;
import com.vquick.dao.persist.UserDaoService;
import com.vquick.rest.service.RestService;
import com.vquick.rest.service.data.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by tigran on 1/31/16.
 */
@Controller
@RequestMapping("/service")
public class RestServiceImpl implements RestService {

    private static final Log logger = LogFactory.getLog(RestServiceImpl.class);

    @Autowired
    private UserManagerService userManagerService;

    @Override
    public ResponseEntity<String> getUserTasks() {
        return new ResponseEntity<String>("Request Succeed", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            logger.info(String.format("[Vquick][%s][createUser] Passed [%s] data to create new user account.", RestServiceImpl.class.getSimpleName(), user.toString()));
            userManagerService.addUser(user);
            logger.info(String.format("[Vquick][%s][createUser] The [%s] user account has been created successfully.", RestServiceImpl.class.getSimpleName(), user.getUserName()));
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Response> deleteUser(@RequestParam(required = true, value = "userName") String userNamer) {
        try {
            logger.info(String.format("[Vquick][%s][deleteUser] Trying to delete data of [%s] user.", RestServiceImpl.class.getSimpleName(), userNamer));
            userManagerService.deleteUser(userNamer);
            logger.info(String.format("[Vquick][%s][createUser] Data of [%s] user account has been deleted successfully.", RestServiceImpl.class.getSimpleName(), userNamer));
            return new ResponseEntity<Response>(new Response(String.valueOf(HttpStatus.OK.value()), "User has been deleted successfully"), HttpStatus.OK);
        } catch (DaoException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<Response>(new Response(String.valueOf(HttpStatus.LOCKED.value()), "Failed to delete data from db."), HttpStatus.LOCKED);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
        }
    }


}
