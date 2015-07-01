/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.functions;

import employee.rest.Employee;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import employee.rest.IndividualData;
import java.util.HashMap;
import my.email.Inbox;
import my.email.MyNamingStrategy;
import my.email.Sent;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author tigran
 */
public class DBFunctions {

    private static SessionFactory factory;
    private static SessionFactory inboxFactory;
    private static SessionFactory sendBoxFactory;
    private static SessionFactory mailFactory;
    private DBConnectionStabilizator dbConnection = new DBConnectionStabilizator();
    Configuration config;

    public DBFunctions() {
        try {
            dbConnection.establishConnection();
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /* Method to add an employee's individual data record in the database */
    public IndividualData addIndividualData(String country, String city,
            String gender, String email, int age) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer dataID = null;
        IndividualData employeeData = null;
        try {
            tx = session.beginTransaction();
            employeeData = new IndividualData(country, city,
                    gender, email, age);
            dataID = (Integer) session.save(employeeData);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeData;
    }

    /* Method to add an employee record in the database */
    public Integer addEmployee(String fullName, String address,
            int salary, String userName, String password,
            IndividualData individualData, String swid) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try {
            tx = session.beginTransaction();
            IndividualData employeeData = individualData;
            Employee employee = new Employee(salary, fullName, address,
                    userName, password, individualData, swid);
            session.save(employeeData);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        createInboxTableForSpecifiedUser(userName);
        createSentTableForSpecifiedUser(userName);
        return employeeID;
    }

    public List<Employee> getAllEmployees() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Employee> employeesList = new ArrayList<Employee>();
            List employees = session.createQuery("FROM Employee").list();
            ObjectMapper mapper = new ObjectMapper();
            Employee employee = null;
            for (Iterator iterator
                    = employees.iterator(); iterator.hasNext();) {
                employee = (Employee) iterator.next();
                employeesList.add(mapper.readValue(
                        employee.toString(), Employee.class));
            }
            tx.commit();
            return employeesList;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Failed to parse data to Employee class");
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return null;
    }

    public boolean validateCredentials(String userName, String password) {
        List<Employee> employeesList = getAllEmployees();
        HashMap<String, String> userNamesPasswords = new HashMap<>();
        for (Employee employee : employeesList) {
            userNamesPasswords.put(employee.getUserName(), employee.getPassword());
        }
        if (userNamesPasswords.containsKey(userName)
                && userNamesPasswords.get(userName).equals(password)) {
            return true;
        }
        return false;
    }

    public boolean deleteEmployeeData(String userName) {
        Session session = factory.openSession();
        Criteria employeeCr = session.createCriteria(Employee.class);
        Criteria employeeDataCr = session.createCriteria(IndividualData.class);
        employeeCr.add(Restrictions.eq("userName", userName));
        try {
            List employees = employeeCr.list();
            System.out.println("=================iiiiiiiiiiii == "
                    + employees.size());
            if (0 == employees.size()) {
                return false;
            }
            session.getTransaction().begin();
            for (Iterator iterator1
                    = employees.iterator(); iterator1.hasNext();) {
                Employee employee = (Employee) iterator1.next();
                employeeDataCr.add(Restrictions.eq("id", employee.getId()));
                IndividualData employeeData = (IndividualData) employeeDataCr.list().get(0);
                System.out.println("uuuuuuuuuuuuuuu  " + employeeData.toString() + " ppppp");
                session.delete(employee);
                session.delete(employeeData);
            }
            session.getTransaction().commit();
            dbConnection.deleteTable("INBOX" + userName);
            dbConnection.deleteTable("SENT" + userName);
            return true;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean updateEmployeeSalary(String fullName, String address, int salary) {
        Session session = factory.openSession();
        Criteria cr = session.createCriteria(Employee.class);
        cr.add(Restrictions.eq("fullName", fullName));
        cr.add(Restrictions.eq("address", address));
        try {
            List employees = cr.list();
            System.out.println("=================iiiiiiiiiiii == "
                    + employees.size());
            if (0 == employees.size()) {
                return false;
            }
            session.getTransaction().begin();
            for (Iterator iterator1
                    = employees.iterator(); iterator1.hasNext();) {
                Employee employee = (Employee) iterator1.next();
                employee.setSalary(salary);
                session.update(employee);
            }
            session.getTransaction().commit();
            System.out.println("ooooooooooooooooookkkkkkkkkkkkkkkkkk");
            return true;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public Employee loginToSystem(String userName, String password) {
        Session session = factory.openSession();
        Criteria cr = session.createCriteria(Employee.class);
        cr.add(Restrictions.eq("userName", userName));
        cr.add(Restrictions.eq("password", password));
        try {
            List employees = cr.list();
            System.out.println("=================login== "
                    + employees.size());
            if (0 == employees.size()) {
                return null;
            }
            Employee a = (Employee) employees.get(0);
            System.out.println("data=========" + a.toString());
            return a;
        } catch (Exception e) {
            System.err.println("********In login catch block*************");
            return null;
        } finally {
            session.close();
        }
    }

    public boolean updateEmployeePassword(String userName, String oldPassword,
            String newPassword) {
        Session session = factory.openSession();
        Criteria cr = session.createCriteria(Employee.class);
        cr.add(Restrictions.eq("userName", userName));
        cr.add(Restrictions.eq("password", oldPassword));
        try {
            List employees = cr.list();
            System.out.println("=================iiiiiiiiiiii == "
                    + employees.size());
            if (0 == employees.size()) {
                return false;
            }
            session.getTransaction().begin();
            for (Iterator iterator1
                    = employees.iterator(); iterator1.hasNext();) {
                Employee employee = (Employee) iterator1.next();
                employee.setPassword(newPassword);
                session.update(employee);
            }
            session.getTransaction().commit();
            System.out.println("The password has been changed successfully");
            return true;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public void createInboxTableForSpecifiedUser(String userName) {
        try {
            inboxFactory = new AnnotationConfiguration().
                    configure().
                    addAnnotatedClass(Inbox.class).setNamingStrategy(new MyNamingStrategy(userName)).
                    buildSessionFactory();
            Session session = inboxFactory.openSession();
            Inbox inbox = new Inbox("test", "test", "Valod");
            Transaction tx = null;
            tx = session.beginTransaction();
            session.save(inbox);
            tx.commit();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void createSentTableForSpecifiedUser(String userName) {
        try {
            sendBoxFactory = new AnnotationConfiguration().
                    configure().
                    addAnnotatedClass(Sent.class).setNamingStrategy(new MyNamingStrategy(userName)).
                    buildSessionFactory();
            Session session = sendBoxFactory.openSession();
            Sent sent = new Sent("test", "test", "VZGO");
            Transaction tx = null;
            tx = session.beginTransaction();
            session.save(sent);
            tx.commit();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public boolean sendMessage(String sender, Sent messageData) {
        try {
            sendBoxFactory = new AnnotationConfiguration().
                    configure().
                    addAnnotatedClass(Sent.class).setNamingStrategy(new MyNamingStrategy(sender)).
                    buildSessionFactory();
            inboxFactory = new AnnotationConfiguration().
                    configure().
                    addAnnotatedClass(Inbox.class).setNamingStrategy(new MyNamingStrategy(messageData.getRecipients())).
                    buildSessionFactory();
            Session sendingSession = sendBoxFactory.openSession();
            Session inboxSession = inboxFactory.openSession();
            Transaction tx = sendingSession.beginTransaction();
            Transaction tx1 = inboxSession.beginTransaction();
            sendingSession.save(messageData);
            Inbox inbox = new Inbox(messageData.getMessageText(), messageData.getMessageSubject(), sender);
            inboxSession.save(inbox);
            tx.commit();
            tx1.commit();
            System.out.println("Sent message");
            return true;
        } catch (Exception ex) {
            System.out.println("Failed to create sessionFactory object." + ex.getMessage());
            return false;
        }
    }

    public List<Inbox> getInbox(String userName) {
        inboxFactory = new AnnotationConfiguration().
                configure().
                addAnnotatedClass(Inbox.class).
                setNamingStrategy(new MyNamingStrategy(userName)).
                buildSessionFactory();
        Session session = inboxFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Inbox> inboxList = new ArrayList<Inbox>();
            List inboxes = session.createSQLQuery("select * from INBOX" + userName).addEntity(Inbox.class).list();
            ObjectMapper mapper = new ObjectMapper();
            Inbox inbox = null;
            for (Iterator iterator
                    = inboxes.iterator(); iterator.hasNext();) {
                inbox = (Inbox) iterator.next();
                inboxList.add(mapper.readValue(
                        inbox.toString(), Inbox.class));
            }
            tx.commit();
            return inboxList;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Failed to parse data to Inbox class");
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return null;
    }

    public List<Sent> getSentBox(String userName) {
        sendBoxFactory = new AnnotationConfiguration().
                configure().
                addAnnotatedClass(Sent.class).
                setNamingStrategy(new MyNamingStrategy(userName)).
                buildSessionFactory();
        Session session = sendBoxFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Sent> sendBoxList = new ArrayList<Sent>();
            List sendBoxes = session.createSQLQuery("select * from SENT" + userName).addEntity(Sent.class).list();
            ObjectMapper mapper = new ObjectMapper();
            Sent sent = null;
            for (Iterator iterator
                    = sendBoxes.iterator(); iterator.hasNext();) {
                sent = (Sent) iterator.next();
                sendBoxList.add(mapper.readValue(
                        sent.toString(), Sent.class));
            }
            tx.commit();
            return sendBoxList;
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Failed to parse data to Sent class");
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }
        return null;
    }

    public Inbox getInboxMessageByID(String userName, int id) {
        List<Inbox> allInboxMessages = getInbox(userName);
        for (Inbox message : allInboxMessages) {
            if (message.getMessageNum() == id) {
                return message;
            }
        }
        return null;
    }

    public Sent getSentMessageByID(String userName, int id) {
        List<Sent> allSentMessages = getSentBox(userName);
        for (Sent message : allSentMessages) {
            if (message.getMessageNum() == id) {
                return message;
            }
        }
        return null;
    }

    public boolean removeSpecifiedInboxMessage(String userName, int messageId) {
        inboxFactory = new AnnotationConfiguration().
                configure().
                addAnnotatedClass(Inbox.class).
                setNamingStrategy(new MyNamingStrategy(userName)).
                buildSessionFactory();
        Session session = inboxFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List inboxes = session.createSQLQuery("select * from INBOX" + userName).addEntity(Inbox.class).list();
            ObjectMapper mapper = new ObjectMapper();
            Inbox inbox = null;
            for (Iterator iterator
                    = inboxes.iterator(); iterator.hasNext();) {
                inbox = (Inbox) iterator.next();
                if (inbox.getMessageNum() == messageId) {
                    session.delete(inbox);
                    tx.commit();
                    return true;
                }
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public boolean removeSpecifiedSentMessage(String userName, int messageId) {
        sendBoxFactory = new AnnotationConfiguration().
                configure().
                addAnnotatedClass(Sent.class).
                setNamingStrategy(new MyNamingStrategy(userName)).
                buildSessionFactory();
        Session session = sendBoxFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List sendBoxes = session.createSQLQuery("select * from SENT" + userName).addEntity(Sent.class).list();
            ObjectMapper mapper = new ObjectMapper();
            Sent sent = null;
            for (Iterator iterator
                    = sendBoxes.iterator(); iterator.hasNext();) {
                sent = (Sent) iterator.next();
                if (sent.getMessageNum() == messageId) {
                    session.delete(sent);
                    tx.commit();
                    return true;
                }
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

}
