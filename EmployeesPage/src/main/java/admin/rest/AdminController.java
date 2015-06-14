/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.rest;

import org.springframework.web.servlet.mvc.SimpleFormController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tigran
 */
public class AdminController extends SimpleFormController {

    public AdminController() {
        setCommandClass(Admin.class);
        setCommandName("loginPage");
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        Admin admin = (Admin) command;
        HttpSession session = request.getSession(true);
        session.setAttribute("userName", admin.getUserName());
        session.setAttribute("password", admin.getPassword());
        System.out.println(admin);
        System.out.println("************logged in******************");
        return new ModelAndView("AdminPage", "admin", admin);
    }
}
