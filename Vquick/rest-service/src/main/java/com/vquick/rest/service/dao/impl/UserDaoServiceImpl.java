package com.vquick.rest.service.dao.impl;

import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;
import com.vquick.rest.service.dao.UserDaoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tigran on 1/31/16.
 */
//@Service("userDaoService")
@Deprecated
public class UserDaoServiceImpl implements UserDaoService {

    private static final Log logger = LogFactory.getLog(UserDaoServiceImpl.class);

/*    @Autowired
    private SessionFactory sessionFactory;*/

    public void addUser(User user) throws DaoException {
        try {
            //sessionFactory.getCurrentSession().save(user);
            throw  new HibernateException("oooooooooooooooo");
        } catch (HibernateException e) {
            logger.error(String.format("[Vquick][%s][addUser] Failed to create new %s user", UserDaoServiceImpl.class.getSimpleName(), user.getUserName()), e);
            throw new DaoException(e);
        }
    }

}
