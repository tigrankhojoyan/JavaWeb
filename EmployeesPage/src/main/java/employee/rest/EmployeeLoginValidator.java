/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 *
 * @author tigran
 */
public class EmployeeLoginValidator implements Validator{

    @Override
    public boolean supports(Class clazz) {
        return EmployeeLogin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeUserName",
                "required.userName", "Field name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeePassword",
                "required.password", "Field name is required.");
       EmployeeLogin employee = (EmployeeLogin) obj;

        if (!(employee.getEmployeePassword().equals("myPass"))) {
            errors.rejectValue("employeePassword", "notmatch.password");
        }

        if (!(employee.getEmployeeUserName().equals("Admin"))) {
            errors.rejectValue("employeeUserName", "notmatch.userName");
        }
    }
    
}
