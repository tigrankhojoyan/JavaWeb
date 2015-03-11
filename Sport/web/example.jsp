<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : example
    Created on : Mar 2, 2015, 10:55:43 PM
    Author     : tigran
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            ul li {
                position: relative;
            }

            ul ul {
                position: absolute;
                top: 1em;
                left: 0;
                display: none;
            }

            ul > li:hover ul {
                display: block;
            }
        </style>
    </head>
    <body>
        <ul>
            <li>Simple List item
                <ul>
                    <li>sub menu item 1</li>
                    <li>sub menu item 2</li>
                    <li>sub menu item 3</li>
                </ul>
            </li>
        </ul>
        <sql:query var="create" dataSource="org.hibernate.dialect.MySQLDialect">
            SELECT * FROM users
        </sql:query>
    </body>
</html>
