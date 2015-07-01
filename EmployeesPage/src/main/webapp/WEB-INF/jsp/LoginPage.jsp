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
        <script type="text/javascript">
            
            function OnSubmitLoginForm() {
                var employeeUserName = document.getElementById("employeeUserName").value;
                var employeePassword = document.getElementById("employeePassword").value;
                if(validateUsername(employeeUserName) && validatePassword(employeePassword)) {
                    var requestBody = buildEmployeeLoginBody(employeeUserName, employeePassword);
                    var connection = sendRequest("POST", "employee/data/login", requestBody);
                    if(connection.status == 200) {
                        window.location.assign("/EmployeesPage/EmployeePage.htm");
                    } else {
                        alert("Username or password is incorrect. Try with right credentials!");
                    }
                } else {
                    alert("chok");
                }

            }

            function validateUsername(userName) {
                if ((userName.length > 5) && (userName.length < 15)) {
                    document.getElementById("employeeUserNameError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("employeeUserNameError").style.display = "block";
                    return false;
                }
            }
            
            function validatePassword(password) {
                if ((password.length > 5) && (password.length < 15)) {
                    document.getElementById("employeePasswordError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("employeePasswordError").style.display = "block";
                    return false;
                }
            }
            
            function sendRequest(method, url, body) {
                var xmlhttp;
                if (window.XMLHttpRequest)
                {// code for IE7+, Firefox, Chrome, Opera, Safari
                    xmlhttp = new XMLHttpRequest();
                }
                else
                {// code for IE6, IE5
                    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                }
                xmlhttp.open(method, url, false);
                if ("" == body) {
                    xmlhttp.send();
                } else {
                    xmlhttp.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
                    xmlhttp.setRequestHeader('Authorization', 'FD aw4567123edkk99');
                    // send the collected data as JSON
                    xmlhttp.send(JSON.stringify(body));
                }
                //return xmlhttp.responseText;
                return xmlhttp;
            }

            function buildEmployeeLoginBody(userName, oldPassword, newPassword) {
                employeeLoginRequestBody = {};
                employeeLoginRequestBody["userName"] = userName;
                employeeLoginRequestBody["password"] = oldPassword;
                return employeeLoginRequestBody;
            }
            
        </script>
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
                    <form name="EmployeeLogin" method="POST"  action="javascript:OnSubmitLoginForm()">
                        <div class="studentTitle">Employee Login</div>
                        <table>
                            <tr>
                                <td>USER NAME</td>
                                <td><input type="text" name="employeeUserName" id="employeeUserName" value="" size="15" /></td>
                                <td class="error" id="employeeUserNameError" style="display: none">
                                    The username of the employee is syntaxly incorrect.    
                                </td>
                            </tr>
                            <tr>
                                <td>PASSWORD</td>
                                <td><input type="password" name="employeePassword" id="employeePassword" value="" size="15" />
                                </td>
                                <td class="error" id="employeePasswordError" style="display: none">
                                    The password of the employee is syntaxly incorrect.    
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

