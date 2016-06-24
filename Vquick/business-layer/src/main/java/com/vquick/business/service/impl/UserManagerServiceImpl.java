package com.vquick.business.service.impl;

import com.vquick.business.exception.BusinessException;
import com.vquick.business.manager.UserManager;
import com.vquick.business.service.UserManagerService;
import com.vquick.business.validator.UserValidator;
import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tigran on 4/25/16.
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    private UserManager userManager;

    public void addUser(User user) throws DaoException, BusinessException {
        UserValidator.validate(user);
        userManager.addUser(user);

    }

    @Override
    public void deleteUser(String userName) throws DaoException, BusinessException {
        UserValidator.validateUserName(userName);
        userManager.deleteUser(userName);
    }


}
