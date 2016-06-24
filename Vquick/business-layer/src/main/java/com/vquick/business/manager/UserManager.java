package com.vquick.business.manager;

import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;

/**
 * Created by tigran on 4/30/16.
 */
public interface UserManager {

    void addUser(User user) throws DaoException;

    void deleteUser(String userName) throws DaoException;
}
