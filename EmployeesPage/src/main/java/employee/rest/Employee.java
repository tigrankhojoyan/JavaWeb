/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

import java.util.UUID;


/**
 *
 * @author tigran
 */
public class Employee {

    private int salary;
    private int id;
    private String fullName;
    private String address;
    private String password;
    private String userName;
    private String swid;
    private IndividualData employeeData;
    

    public Employee(int salary, String fullName,
            String address, String userName, 
            String password, IndividualData employeeData) {
        this.salary = salary;
        this.fullName = fullName;
        this.address = address;
        this.employeeData = employeeData;
        this.password = password;
        this.userName = userName;
        this.swid = "{" + UUID.randomUUID().toString() + "}";
    }
    
    public Employee(int salary, String fullName,
            String address) {
        this.salary = salary;
        this.fullName = fullName;
        this.address = address;
        this.employeeData = new IndividualData("", "", "", "", 0);
    }
    
    public Employee(String userName, String password) {
       this.userName = userName;
       this.password = password;
    }

    public Employee() {

    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public IndividualData getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(IndividualData employeeData) {
        this.employeeData = employeeData;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSwid() {
        return swid;
    }

    public void setSwid(String swid) {
        this.swid = swid;
    }
    

    @Override
    public String toString() {
        IndividualData employeeData = getEmployeeData();
        String employeeString = "{\"fullName\":\"" + getFullName()
                + "\", \"userName\": \"" + getUserName() + 
                "\", \"password\": \"" + getPassword() + "\", "
                + "\"address\":\"" + getAddress() + "\", \"salary\":"
                + getSalary() + ",\"id\":"
                + getId() + ", \"employeeData\":"
                + employeeData.toString() + ", \"swid\": \"" 
                + getSwid() + "\"}";
        return employeeString;
    }

}
