package com.vquick.dao.persist;

import com.vquick.dao.data.User;
import com.vquick.dao.data.UserData;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by tigran on 1/31/16.
 */
public final class CrudFunctions {

    private static final Log logger = LogFactory.getLog(CrudFunctions.class);
    private static SessionFactory factory;


    public CrudFunctions() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            logger.error("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Adds the new user data into database.
     *
     * @param userData - user data
     * @return positive num, if data was written successfully, -1 otherwise.
     */
    public static UserData addUserData(UserData userData) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer dataID = -1;
        try {
            logger.debug("Starting to write given userData: [{" + userData +"}] data to db.");
            tx = session.beginTransaction();
            session.save(userData);
            tx.commit();
            logger.debug("Given 'userData' data has been successfully added into database!");
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            return userData;
        }
    }

    /**
     * Adds the new user  into database.
     *
     * @param user - user data
     * @return positive num, if data was written successfully, -1 otherwise.
     */
    public static User addUser(User user) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            logger.debug("Starting to write given user: [{" + user +"}] data to db.");
            tx = session.beginTransaction();
            session.save(user.getUserData());
            session.save(user);
            tx.commit();
            logger.debug("Given 'user' data has been successfully added into database!");
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            return user;
        }
    }
}
