package com.vquick.business.manager.impl;

import com.vquick.business.config.Base;
import com.vquick.business.manager.UserManager;
import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;
import com.vquick.dao.persist.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tigran on 4/25/16.
 */
@Service
@EnableTransactionManagement
public class UserManagerImpl extends Base implements UserManager  {

    @Autowired
    private UserDaoService userDaoService;

    @Override
    @Transactional
    public void addUser(User user) throws DaoException {
        userDaoService.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(String userName) throws DaoException {
        userDaoService.deleteUser(userName);
    }

}
