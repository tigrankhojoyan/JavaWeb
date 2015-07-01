/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author tigran
 */
@Controller
public class EmployeeLoginController extends SimpleFormController {
    
      public EmployeeLoginController() {
        setCommandClass(EmployeeLogin.class);
        setCommandName("employeeForm");
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        EmployeeLogin employeeLogin = (EmployeeLogin) command;
        /*HttpSession session = request.getSession(true);
        session.setAttribute("employeeUserName", employeeLogin.getEmployeeUserName());
        session.setAttribute("employeePassword", employeeLogin.getEmployeePassword());
        System.out.println(employeeLogin);
        System.out.println("************logged in******************");*/
        return new ModelAndView("EmployeePage", "employeeLogin", employeeLogin);
    }
    
}
