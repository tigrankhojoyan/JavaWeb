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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tigran
 */
public class DatabaseFunctionalities extends HttpServlet {

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
            getSpecifiedClubsUsingSessions(request, response, "England");
            getSpecifiedClubsUsingSessions(request, response, "Germany");
            getSpecifiedClubsUsingSessions(request, response, "Italy");
            getSpecifiedClubsUsingSessions(request, response, "Spain");
            getSpecifiedClubsSitesUsingSession(request, response, "englishSites");
            getSpecifiedClubsSitesUsingSession(request, response, "germanSites");
            getSpecifiedClubsSitesUsingSession(request, response, "italianSites");
            getSpecifiedClubsSitesUsingSession(request, response, "spanishSites");
            //request.setAttribute("test", "test");
            String url = "/index.jsp"; //relative url for display jsp page
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);
            //response.sendRedirect("/Sport/index.jsp");
        }
    }

    protected void getSpecifiedClubsUsingRequest(HttpServletRequest request, HttpServletResponse response,
            String country) throws ServletException, IOException {
        List<String> clubNames = new ArrayList<String>();
        // JDBC driver name and database URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/football";

        //  Database credentials
        String USER = "root";
        String PASS = "zaqxsw12";

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Database Result";
        out.println(
                "<h1 align=\"center\">" + title + "</h1>\n");
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT " + country + " FROM clubs";
            ResultSet rs = stmt.executeQuery(sql);          
            while (rs.next()) {
                //Retrieve by column name
                String clubName = rs.getString(country);
                clubNames.add(clubName);               
            }           
            request.setAttribute(country, clubNames);
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
    
   protected void getSpecifiedClubsSitesUsingRequest(HttpServletRequest request, HttpServletResponse response,
            String clubsNation) throws ServletException, IOException {
        List<String> clubSitesLoactions = new ArrayList<String>();
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
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT " + clubsNation + " FROM clubSites";
            ResultSet rs = stmt.executeQuery(sql);          
            while (rs.next()) {
                //Retrieve by column name
                String clubName = rs.getString(clubsNation);
                clubSitesLoactions.add(clubName);               
            }           
            request.setAttribute(clubsNation, clubSitesLoactions);
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
   protected void getSpecifiedClubsSitesUsingSession(HttpServletRequest request, HttpServletResponse response,
            String clubsNation) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<String> clubSitesLoactions = new ArrayList<String>();
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
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT " + clubsNation + " FROM clubSites";
            ResultSet rs = stmt.executeQuery(sql);          
            while (rs.next()) {
                //Retrieve by column name
                String clubName = rs.getString(clubsNation);
                clubSitesLoactions.add(clubName);               
            }           
            request.setAttribute(clubsNation, clubSitesLoactions);
            session.setAttribute(clubsNation, clubSitesLoactions);
            session.setMaxInactiveInterval(60*60);
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
   
   protected void getSpecifiedClubsUsingSessions(HttpServletRequest request, HttpServletResponse response,
            String country) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<String> clubNames = new ArrayList<String>();
        // JDBC driver name and database URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/football";

        //  Database credentials
        String USER = "root";
        String PASS = "zaqxsw12";

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Database Result";
        out.println(
                "<h1 align=\"center\">" + title + "</h1>\n");
        try {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT " + country + " FROM clubs";
            ResultSet rs = stmt.executeQuery(sql);          
            while (rs.next()) {
                //Retrieve by column name
                String clubName = rs.getString(country);
                clubNames.add(clubName);               
            }           
            session.setAttribute(country, clubNames);
            session.setMaxInactiveInterval(60*60);
            //request.setAttribute("loginStatus", "oooooo");
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
