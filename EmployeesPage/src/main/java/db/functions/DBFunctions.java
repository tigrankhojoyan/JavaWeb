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
/**
 *
 * @author tigran
 */
public class DBFunctions {

    private static SessionFactory factory;

    public DBFunctions() {
        try {
            DBConnectionStabilizator dbConnection = new DBConnectionStabilizator();
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

}

