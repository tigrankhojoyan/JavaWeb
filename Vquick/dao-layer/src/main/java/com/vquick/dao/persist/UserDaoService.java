package com.vquick.dao.persist;

import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;
import org.springframework.stereotype.Service;

/**
 * Created by tigran on 4/25/16.
 */
public interface UserDaoService {

    void addUser(final User user) throws DaoException;

    User getUser(final String userName);

    void deleteUser(final String userName) throws DaoException;

}
