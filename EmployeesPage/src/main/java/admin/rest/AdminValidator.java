/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.rest;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
/**
 *
 * @author tigran
 */
public class AdminValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Admin.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
                "required.userName", "Field name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Field name is required.");
        Admin admin = (Admin) obj;

        if (!(admin.getPassword().equals("myPass"))) {
            errors.rejectValue("password", "notmatch.password");
        }

        if (!(admin.getUserName().equals("Admin"))) {
            errors.rejectValue("userName", "notmatch.userName");
        }
    }

}
