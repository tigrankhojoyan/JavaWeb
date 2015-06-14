<%-- 
    Document   : EmployeePage
    Created on : Jun 14, 2015, 1:14:32 PM
    Author     : tigran
--%>
<%@page import="db.functions.DBFunctions"%>
<%@page import="java.lang.String"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome to Employee page!</h1>
        <table id="usPass">
            <tr>
                <td>UserName :</td><td>${employeeLogin.getEmployeeUserName()}</td>
            </tr>
            <tr>
                <td>Password :</td><td>${employeeLogin.getEmployeePassword()}</td>
            </tr>
        </table>
    </body>
</html>
