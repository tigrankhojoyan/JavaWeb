/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.db.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author tigran
 */
public class Registration extends HttpServlet {
    private Connection conn;
    private Statement stmt;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Registration</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Registration at " + request.getContextPath() + "</h1>");
            /*out.println(request.getParameter("firstName"));
            out.println(request.getParameter("lastName"));
            out.println(request.getParameter("email"));*/
            registerNewAccount(request, response);
            String url = "/index.jsp"; //relative url for display jsp page
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            //response.sendRedirect("/Sport/index.jsp");
            rd.forward(request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void registerNewAccount(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        // JDBC driver name and database URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/football";

        //  Database credentials
        String USER = "root";
        String PASS = "zaqxsw12";

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println("1");
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            out.println("2");
            sql = "SELECT * from users where username=" + request.getParameter("username");
            ResultSet rs = stmt.executeQuery(sql);          
            out.println("3");
            while(rs.next()) {
                request.setAttribute("RegistrationError", 
                        "The username you provided already exists. Try to register with another username!");
                return;
            }
            out.println("4");
            sql = "Select * from users where mail=\"" + request.getParameter("email") + "\"";
            rs = stmt.executeQuery(sql); 
            while (rs.next()) {
                //Retrieve by column name
                request.setAttribute("RegistrationError", 
                        "The mail you provided already is in use. Try to register with another email!");              
                return;
            }
            out.println("5");
            sql = "insert into users(firstname,lastname,username,password,country,city,gender,mail) values(\"" +
                    request.getParameter("firstName") + "\",\"" + request.getParameter("lastName") + 
                    "\",\"" + request.getParameter("registrationUsername") + "\",\"" + 
                    request.getParameter("setPassword") + "\",\"" +
                    request.getParameter("country") + "\",\"" +
                    request.getParameter("city") + "\",\"" + 
                    request.getParameter("gender") + "\",\"" + 
                    request.getParameter("email") + "\")";
            out.println(sql);
            stmt.executeUpdate(sql); 
            request.setAttribute("loginStatus", "OK");
            request.setAttribute("fullName", request.getParameter("firstName") + 
                    " " + request.getParameter("lastName"));
            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
