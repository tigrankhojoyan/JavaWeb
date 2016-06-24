package com.vquick.business.service;

import com.vquick.business.exception.BusinessException;
import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;

/**
 * Created by tigran on 4/25/16.
 */
public interface UserManagerService {

    void addUser(final User user) throws DaoException, BusinessException;

    void deleteUser(final String userName) throws DaoException, BusinessException;
}
