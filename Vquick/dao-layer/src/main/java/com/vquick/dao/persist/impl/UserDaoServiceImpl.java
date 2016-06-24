package com.vquick.dao.persist.impl;

import com.vquick.dao.data.User;
import com.vquick.dao.exception.DaoException;
import com.vquick.dao.persist.UserDaoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by tigran on 1/31/16.
 */
@Repository("userDaoService")
public class UserDaoServiceImpl implements UserDaoService {

    private static final Log logger = LogFactory.getLog(UserDaoServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void addUser(User user) throws DaoException {
        try {
            if(getUser(user.getUserName()) != null) {
                logger.error(String.format("[Vquick][%s][addUser] User account by '%s' userName already exist.", UserDaoServiceImpl.class.getSimpleName(), user.getUserName()));
                throw new DaoException(String.format("User account by '%s' userName already exist.", user.getUserName()));
            }
            sessionFactory.getCurrentSession().save(user);
        } catch (HibernateException e) {
            logger.error(String.format("[Vquick][%s][addUser] Failed to create new %s user", UserDaoServiceImpl.class.getSimpleName(), user.getUserName()), e);
            throw new DaoException(e);
        }
    }

    @Override
    public User getUser(String userName) {
        try {
            User user = (User) sessionFactory.getCurrentSession().createCriteria(User.class)
                    .add(Restrictions.eq("userName", userName)).uniqueResult();
            return user;
        } catch (HibernateException e) {
            logger.error(String.format("[Vquick][%s][getUser] Failed to get user account by '%s' username.", UserDaoServiceImpl.class.getSimpleName(), userName));
            return null;
        }

    }

    @Override
    public void deleteUser(String userName) throws DaoException {
        try {
            User user = getUser(userName);
            if (null == user) {
                logger.error(String.format("[Vquick][%s][deleteUser] Failed to delete user account by '%s' username, which does not exist.", UserDaoServiceImpl.class.getSimpleName(), userName));
                throw new DaoException(String.format("User account by '%s' username does not exist.", userName));
            }
            sessionFactory.getCurrentSession().delete(user);
        } catch (HibernateException e) {
            logger.error(String.format("[Vquick][%s][deleteUser] Failed to delete '%s' user account.", UserDaoServiceImpl.class.getSimpleName(), userName), e);
            throw new DaoException(e);
        }
    }

}
