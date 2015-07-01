/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author tigran
 */
public class EmployeeLogin {

    @NotEmpty(message = "Please enter your userName.")
    @Size(min = 8, max = 15, message = "Your userName must between 6 and 15 characters")
    private String employeeUserName;

    @NotEmpty(message = "Please enter your password.")
    @Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
    private String employeePassword;

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

}
