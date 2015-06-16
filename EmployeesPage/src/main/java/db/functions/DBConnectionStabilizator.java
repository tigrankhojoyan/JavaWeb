/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.functions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tigran
 */
public class DBConnectionStabilizator {

    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost/EmployeesProject";

    //  Database credentials
    private final String USER = "root";
    private final String PASS = "zaqxsw12";
    private Connection conn;
    private Statement stmt;

    /**
     * Connects to db using given url.
     *
     * @param dbURL
     * @return
     * @throws SQLException
     */
    private boolean connectToDB(String dbURL) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection(dbURL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to find 'com.mysql.jdbc.Driver' class.\n"
                    + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Filed to connect to db.\n"
                    + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Creates 'userData' database.
     *
     * @throws SQLException
     */
    private void createDBAndTable() throws SQLException {
        try {
            connectToDB("jdbc:mysql://localhost/");
            String sqlQuery = "CREATE DATABASE EmployeesProject";
            stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            System.err.println("Failed to create db." + e.getMessage());
        }
    }

    /**
     * Creates EmployeesProject database if it does not exist.
     * 
     * @throws SQLException 
     */
    public void establishConnection() throws SQLException {
        if (!connectToDB("jdbc:mysql://localhost/EmployeesProject")) {
            createDBAndTable();
        }
    }

    /**
     * Deletes the specified table from EmployeesProject DB
     * @param tableName 
     */
    public void deleteTable(String tableName) {
        try {
            connectToDB("jdbc:mysql://localhost/EmployeesProject");
        } catch (Exception e) {
            System.out.println("The EmployeesProject db does not exist!\n"
                    + e.getMessage());
        }
        String sqlQuery = "drop table " + tableName;
        try {
            stmt.executeUpdate(sqlQuery);
        } catch (SQLException ex) {
            System.err.println("Failed to delete '" + tableName + 
                    "' table.\n" + ex.getMessage());
        }
    }
}
