package com.vquick.rest.service.dao;

import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;

/**
 * Created by tigran on 4/25/16.
 */
@Deprecated
public interface UserDaoService {

    void addUser(final User user) throws DaoException;
}
