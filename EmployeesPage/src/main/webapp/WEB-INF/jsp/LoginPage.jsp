<%-- 
    Document   : LoginPage
    Created on : May 31, 2015, 12:38:59 PM
    Author     : tigran
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
            body {
                background-color: #CCFFFF;
                overflow-x: scroll;
            }

            .page {
                position: absolute;
                width: 100%;
                height: 100%;
            }

            .topDiv {
                position: absolute;
                width: 100%;
                height: 100px;
                margin-left: auto;
                margin-right: auto;
                background-color: gray;
                text-align: center;
                font-size: 1.3em;
                font-style: bold;
            }

            .leftDiv {
                position: absolute;
                height: 100%;
                width: 150px;
                margin-left: 0px;
                margin-top: 100px;
                background-color: gainsboro;
                font-style: bold;
                font-size: 1.3em;
            }

            .centerDiv {
                margin: 20% auto;
                height: 250px;
                width: 300px;
                background-color: ghostwhite;
                font-style: italic;
                font-size: 1.1em;
                text-align: center;
            }

            .adminTitle {
                background-color: black;
                font-style: bold;
                font-size: 1.4em;
                width: 300 px;
                color: white;
            }
            .studentTitle {
                background-color: brown;
                font-style: bold;
                font-size: 1.4em;
                width: 300 px;
                color: white;
            }

            .error {
                color: #ff0000;
            }
            .errorblock{
                color: #000;
                background-color: #ffEEEE;
                border: 3px solid #ff0000;
                padding:8px;
                margin:16px;
            }

        </style>
    <body>
        <div class="page">
            <div class="centerDiv">

                <div class="adminTitle">Admin Login</div>
                <form:errors path="*" cssClass="errorblock" element="div"/>
                <div id="signIn" style="font-size: 0.8em">
                    <form:form method="POST" commandName="loginPage">
                        <table>
                            <tr>
                                <td>UserName </td>
                                <td><form:input path="userName" /></td>
                                <td><form:errors path="userName" cssClass="error" /></td>
                            </tr>
                            <tr>
                                <td>Password </td>
                                <td><form:password path="password" /></td>
                                <td><form:errors path="password" cssClass="error" /></td>
                            </tr>
                            <tr>
                                <td colspan="3"><input type="submit" /></td>
                            </tr>
                        </table>
                    </form:form>
                    <br>
                    <form name="Registration" action="/Students/webresources/students/studentpage" method="POST">
                        <div class="studentTitle">Student Login</div>
                        <table>
                            <tr>
                                <td>USER NAME</td>
                                <td><input type="text" name="studnetUsername" value="" size="15" /></td>
                            </tr>
                            <tr>
                                <td>PASSWORD</td>
                                <td><input type="password" name="studentPassword" value="" size="15" />
                                </td>
                            </tr>
                        </table>
                        <input type="submit" value="login" name="login" />
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>

