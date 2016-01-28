package com.my.jira.admin.data.dao;

import com.my.jira.admin.data.Task;
import com.my.jira.admin.data.TaskStatuses;
import com.my.jira.admin.data.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigran on 1/18/16.
 */
public class CrudFunctions {

    private final static Logger logger = LoggerFactory.getLogger(CrudFunctions.class);
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
     * @param user - user data
     * @return positive num, if data was written successfully, -1 otherwise.
     */
    public static int writeUserToDb(User user) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer dataID = -1;
        try {
            logger.debug("Starting to write given user: [{}] data to db.", user);
            tx = session.beginTransaction();
            dataID = (Integer) session.save(user);
            tx.commit();
            logger.debug("Given 'user' data has been successfully added into database!");
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            return dataID;
        }
    }

    /**
     * Writes given task data into appropriate database.
     *
     * @param task
     * @return positive num, if data was written successfully, -1 otherwise.
     */
    public static int writeTaskToDb(Task task) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer dataID = -1;
        try {
            logger.debug("Starting to write given task: [{}] data to db.", task);
            tx = session.beginTransaction();
            dataID = (Integer) session.save(task);
            tx.commit();
            logger.debug("Given 'task' data has been successfully added into database!");
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
            return dataID;
        }
    }

    /**
     * Gets the tasks for the specified user
     *
     * @param userId
     * @return
     */
    public static ArrayList<Task> getTasksOfSpecifiedUser(int userId) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Task.class);
        criteria.add(Restrictions.eq("assigneeUserId", userId));
        ArrayList<Task> tasksList = new ArrayList<Task>();

        try {
            List tasks = criteria.list();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = (Task) tasks.get(i);
                tasksList.add(task);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
            return tasksList;
        }
    }

    /**
     * Checks if user by given username exists
     *
     * @param userName
     * @return true, if specified user registered, false otherwise
     */
    public static boolean isSpecifiedUserExist(String userName) {

        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName", userName));

        try {
            logger.info("Checks if [{}] user exist.", userName);
            List users = criteria.list();
            if (users.size() > 0) {
                logger.info("The [{}] user exists.", userName);
                return true;
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }

        logger.info("The [{}] user does not exist.", userName);
        return false;
    }

    /**
     * Deletes specified user data from db
     *
     * @param userName
     * @return - true, if data deleted successfully, false otherwise
     */
    public static CrudStatuses deleteSpecifiedUser(String userName) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName", userName));
        logger.info("Trying to delete the [{}] user account.", userName);

        try {
            session.beginTransaction();
            List users = criteria.list();
            if(users.size() < 1)
                return CrudStatuses.USER_DELETE_NON_EXISTING_USER;
            User user = (User) users.get(0);
            session.delete(user);
            session.getTransaction().commit();
            logger.info("The [{}] user has been deleted successfully.", userName);
            return CrudStatuses.USER_DELETE_SUCCESS;
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }

        return CrudStatuses.USER_DELETE_FAIL;
    }

    /**
     * Deletes specified task(by taskId).
     *
     * @param taskId
     * @return - true, if the task has been deleted successfully, false otherwise
     */
    public static CrudStatuses deleteSpecifiedTask(Integer taskId) {

        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Task.class);
        criteria.add(Restrictions.eq("taskId", taskId));
        logger.info("Trying to delete the task, which id is: [{}].", taskId);

        try {
            session.beginTransaction();
            List tasks = criteria.list();
            if(tasks.size() < 1)
                return  CrudStatuses.TASK_DELETE_NON_EXISTING_TASK;
            Task task = (Task) tasks.get(0);
            session.delete(task);
            logger.info("The task by [{}] id has been deleted successfully.", taskId);
            session.getTransaction().commit();
            return CrudStatuses.TASK_DELETE_SUCCESS;
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }

        return CrudStatuses.USER_DELETE_FAIL;
    }

    /**
     * Gets the data of user specified by userName
     *
     * @param userName
     * @return - the specified username, if it exist, null otherwise
     */
    public static User getSpecifiedUserData(String userName) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName", userName));
        User user = null;

        try {
            logger.info("Checks if the [{}] user exist.", userName);
            List users = criteria.list();
            if (users.size() < 0) {
                logger.info("The [{}] user does not exist.", userName);
            } else {
                user = (User) users.get(0);
                return user;
            }

        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }

        return null;
    }

    /**
     *
     *
     * @param taskId
     * @param userName
     * @param userId
     * @throws Exception
     */
    public static void assignTaskToUser(int taskId, String userName, int userId) throws Exception {
        //
        if(!isSpecifiedUserExist(userName)) {
            throw new Exception("The specified user does not exist");
        }
        //
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Task.class);
        criteria.add(Restrictions.eq("taskId", taskId));

        try {
            session.beginTransaction();
            List tasks = criteria.list();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = (Task) tasks.get(i);
                task.setAssigneeUserId(userId);
                session.update(task);
            }
            logger.info("The task by [{}] id has been assigned to [{}] user successfully.",
                    new Object[] {taskId, userName});
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new Exception("Failed to assign the task by '" + taskId +
                    "' id to '" + userName + "' user");
        } finally {
            session.close();
        }

    }


    /**
     * Updates the status of the specified task.
     *
     * @param taskId
     * @param taskStatus
     * @return
     */
    public static CrudStatuses updateTaskStatus(int taskId, String taskStatus) {

        if(TaskStatuses.valueOf(taskStatus) == null) {
            return CrudStatuses.TASK_STATUS_UPDATE_INVALID;
        }

        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Task.class);
        criteria.add(Restrictions.eq("taskId", taskId));

        try {
            session.beginTransaction();
            List tasks = criteria.list();
            if(tasks.size() > 0) {
                Task task = (Task) tasks.get(0);
                task.setTaskStatus(taskStatus);
                session.update(task);
                logger.info("The status of task by [{}] id has been updated successfully.",
                        new Object[]{taskId});
                session.getTransaction().commit();
                return CrudStatuses.TASK_ASSIGN_STATUS_SUCCESS;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
        }

        logger.info("The task by [{}] status does not exist.",
                new Object[]{taskId});
        return CrudStatuses.TASK_ASSIGN_STATUS_FAIL;
    }

    /**
     * Gets all the tasks
     *
     * @return
     */
    public static ArrayList<Task> getAllTasks() {
        Session session = factory.openSession();
        ArrayList<Task> tasksList = new ArrayList<Task>();

        try {
            session.beginTransaction();
            List tasks = session.createQuery("FROM TASK").list();
            logger.info("Trying to list all the tasks:\n");
            for(int i = 0; i < tasks.size(); i++) {
                Task task = (Task) tasks.get(i);
                tasksList.add(task);
                logger.info("The task with [{}] id is the following.\n [{}]",
                        new Object[]{task.getTaskId(), task.toString()});
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
            return tasksList;
        }

    }

    /**
     * Gets all the users.
     *
     * @return
     */
    public static ArrayList<User> getAllUsers() {
        Session session = factory.openSession();
        ArrayList<User> usersList = new ArrayList<User>();

        try {
            session.beginTransaction();
            List users = session.createQuery("FROM USER").list();
            logger.info("Trying to list all the users:\n");
            for(int i = 0; i < users.size(); i++) {
                User user = (User) users.get(i);
                usersList.add(user);
                logger.info("The task with [{}] id is the following.\n [{}]",
                        new Object[]{user.getUserId(), user.toString()});
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            session.close();
            return usersList;
        }
    }



}
