<%-- 
    Document   : AdminPage
    Created on : May 31, 2015, 4:56:10 PM
    Author     : tigran
--%>

<%@page import="db.functions.DBFunctions"%>
<%@page import="java.lang.String"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
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
                position: absolute;
                width: 100%;
                margin-left: 160px;
                margin-top: 110px;
                height: 80%;
                background-color: ghostwhite;
                font-style: italic;
                font-size: 1.1em;
            }

            #navlist li
            {
                display: inline;
                list-style-type: none;
                margin-left: 20px;
                margin-right: 20px;
            }

            li a {
                text-decoration: none;
                color: green;
            }

            ul li {
                position: relative;
            }

            ul ul {
                position: absolute;
                top: 1em;
                right: 0;
                margin-right: -80px;
                display: none;
                width: 220px;
            }

            ul > li:hover ul {
                display: block;
            }

        </style>
        <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
        <script type="text/javascript">

            function viewEmployees() {
                document.getElementById("usPass").style.display = "none";
                document.getElementById("updateEmployee").style.display = "none";
                document.getElementById("deleteEmployee").style.display = "none";
                document.getElementById("addEmployeeForm").style.display = "none";
                document.getElementById("allEmployees").style.display = "block";
                allStudents = sendRequest("GET", "admin/data/employees/getAllData", "").responseText;
                document.getElementById("allEmployees").innerHTML = allStudents;
            }

            function editEmployee() {
                document.getElementById("usPass").style.display = "none";
                document.getElementById("allEmployees").style.display = "none";
                document.getElementById("deleteEmployee").style.display = "none";
                document.getElementById("addEmployeeForm").style.display = "none";
                document.getElementById("updateEmployee").style.display = "block";
            }

            function removeEmployee() {
                document.getElementById("usPass").style.display = "none";
                document.getElementById("allEmployees").style.display = "none";
                document.getElementById("addEmployeeForm").style.display = "none";
                document.getElementById("updateEmployee").style.display = "none";
                document.getElementById("deleteEmployee").style.display = "block";
            }
            
            function addNewEmployee() {
                document.getElementById("usPass").style.display = "none";
                document.getElementById("allEmployees").style.display = "none";
                document.getElementById("deleteEmployee").style.display = "none";
                document.getElementById("updateEmployee").style.display = "none";
                document.getElementById("addEmployeeForm").style.display = "block";
            }
            
            function OnSubmitAddEmployee() {
                newEmployee = {};
                individualData = {};
                newEmployee["fullName"] = $('#fullName').val();
                newEmployee["userName"] = $('#employeeUserName').val();
                newEmployee["password"] = $('#employeePassword').val();
                newEmployee["address"] = $('#employeeAddress').val();
                newEmployee["salary"] = $('#employeeSalary').val();
                individualData["age"] = $('#employeeAge').val();
                if (document.getElementById("male").checked) {
                    individualData["gender"] = "male";
                } else {
                    individualData["gender"] = "female";
                }
                individualData["email"] = $('#employeeEmail').val();
                individualData["city"] = $('#city').val();
                individualData["country"] = $('#country').val();
                newEmployee["employeeData"] = individualData;
                creationStatus = sendRequest("POST", "admin/data/registration", newEmployee).status;
                alert(creationStatus);
                if(creationStatus == 200) {
                    alert("The '" + newEmployee["fullName"] + "' account was created successfully");
                    clearRegistrationFields();
                } else {
                    alert("Failed to create account. Check the filled in data!");
                }
            }

            function OnSubmitDeleteEmployee() {
                var deletionEmployee = $('#deletionEmployee').val();
                deletionResponse = JSON.parse(sendRequest("DELETE",
                        "admin/data/employees/deleteData?fullName=" + deletionEmployee, "").responseText);
                deletionStatus = deletionResponse.value;
                alert(deletionStatus);
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
                xmlhttp.setRequestHeader('Content-Type', 'application/json');
                xmlhttp.setRequestHeader('Authorization', 'FD aw4567123edkk99');
                if ("" == body) {
                    xmlhttp.send();
                } else {
                    // send the collected data as JSON
                    xmlhttp.send(JSON.stringify(body));
                }
                //alert(xmlhttp.responseText);
                //return xmlhttp.responseText;
                return xmlhttp;
            }

            function OnSubmitModifyEmployee() {
                alert("in submit");
                modificationEmployee = {};
                modificationEmployee["fullName"] = $('#modificationEmployee').val();
                modificationEmployee["address"] = $('#modificationEmployeeAddress').val();
                modificationEmployee["salary"] = $('#finalSalary').val();
                modificationStatus = "{\"UpdateStatus\":\"\",\"Responsecode\":\"\"}";
                modificationStatus = JSON.parse(sendRequest("PUT", "admin/data/employees/updateEmployee", modificationEmployee).responseText);
                alert(modificationStatus.UpdateStatus);
                //document.getElementById("updateEmployee").innerHTML = modificationStatus;
                //alert(modificationEmployee.address);
            }
            
            function clearRegistrationFields() {
                document.getElementById("fullName").value = "";
                document.getElementById("employeeAddress").value = "";
                document.getElementById("employeeSalary").value = "";
                document.getElementById("employeeAge").value = "";
                document.getElementById("employeeEmail").value = "";
                document.getElementById("city").value = "";
                document.getElementById("country").value = "";
                document.getElementById("employeeUserName").value = "";
                document.getElementById("employeePassword").value = "";
            }
            
        </script>
    </head>
    <body>
        <%
            String userName = (String) session.getAttribute("userName");
            String password = (String) session.getAttribute("password");
            valiudateCredentials(userName, password, response);
        %>
        <%!
            void valiudateCredentials(String username, String password, HttpServletResponse response) {
                if (null == username || null == password
                        || !password.equals("myPass") || !username.equals("Admin")) {
                    response.setStatus(response.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", "CredentialsRequired.htm");
                }
            }
        %>
        <div class="page"> 
            <div class="topDiv">
                <h2>Welcome to administrator page!</h2>
            </div>
            <div class ="leftDiv">
                <input type="submit" value="Add Employee" name="AddEmployee" onClick="addNewEmployee()" style="width:130px"/>
                <br>
                <input type="submit" value="View Employees" name="ViewEmployees" onClick="viewEmployees()" style="width:130px;"/>
                <br>
                <input type="submit" value="Edit Employee" name="EditEmployee" onClick="editEmployee()"  style="width:130px;"/>
                <br>
                <input type="submit" value="Remove Employee" name="RemoveEmployee" onClick="removeEmployee()"  style="width:130px;"/>
                <br>
                <input type="submit" value="Find Employee" name="FindEmployee" onClick="findEmployee()"  style="width:130px;"/>
            </div>
            <div class="centerDiv" id="centralDiv">
                <table id="usPass">
                    <tr>
                        <td>UserName :</td><td>${admin.getUserName()}</td>
                    </tr>
                    <tr>
                        <td>Password :</td><td>${admin.getPassword()}</td>
                    </tr>
                </table>
                <div id="allEmployees" style="display: none">
                </div>

                <div id="updateEmployee" style="display: none">
                    <form name="modifyEmployee" method="post" action="javascript:OnSubmitModifyEmployee()">
                        <table>
                            <tr>
                                <td>
                                    Full Name
                                </td>
                                <td>
                                    <input type="text" id="modificationEmployee" name="modificationEmployee" value="" size="40" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Address
                                </td>
                                <td>
                                    <input type="text" id="modificationEmployeeAddress" name="modificationEmployeeAddress" value="" size="40" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Salary
                                </td>
                                <td>
                                    <input type="text" id="finalSalary" name="finalSalary" value="" size="40" />
                                </td>
                            </tr>
                        </table>
                        <input type="submit" value="Change Salary" name="Change Salary" />  
                    </form>
                </div>
                <div id="deleteEmployee" style="display: none">
                    <form name="deleteEmployee" method="post" action="javascript:OnSubmitDeleteEmployee()">
                        <table>
                            <tr>
                                <td>
                                    Full Name
                                </td>
                                <td>
                                    <input type="text" id="deletionEmployee" name="deletionEmployee" value="" size="40" />
                                </td>
                            </tr>
                        </table>
                        <input type="submit" value="Delete Employee" name="Delete Employee" />  
                    </form>
                </div>
                <div id="addEmployeeForm" style="display: none">
                    <form name="addEmployee" method="post" action="javascript:OnSubmitAddEmployee()">
                        <table>
                            <tr>
                                <td> Full Name </td>
                                <td> <input type="text" name="fullName" value="" size="40" id="fullName"/>
                                </td> <td> 
                                    <div id="fullNameError" style="display: none; color:red;"> First Name should contain only alpha symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td> Address </td>
                                <td> <input type="text" name="employeeAddress" value="" size="40" id="employeeAddress"/>
                                </td> <td>
                                    <div id="employeeAddressError" style="display: none; color:red;"> Last Name should contain only alpha symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td> UserName </td>
                                <td> <input type="text" name="employeeUserName" value="" size="40" id="employeeUserName"/>
                                </td> <td>
                                    <div id="employeeUserNameError" style="display: none; color:red;"> Incorrect userName </div>
                                </td>
                            </tr>
                            <tr>
                                <td> Password </td>
                                <td> <input type="password" name="employeePassword" value="" size="40" id="employeePassword"/>
                                </td> <td>
                                    <div id="employeePasswordError" style="display: none; color:red;"> Invalid password </div>
                                </td>
                            </tr>
                            <tr>
                                <td> Mail Address </td>
                                <td> <input type="text" name="employeeEmail" value="" size="40" id="employeeEmail"/>
                                </td> <td>
                                    <div id="employeeEmailError" style="display: none; color:red;"> Invalid email address </div>
                                </td>
                            </tr>
                            <tr> 
                                <td> Salary </td> 
                                <td><input type="text" name="employeeSalary" value="" size="40" id="employeeSalary"/> 
                                </td> <td>
                                    <div id="employeeSalaryError" style="display: none; color:red;"> Username's length must be between 8-15 symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td> Age </td>
                                <td><input type="text" name="employeeAge" value="" size="40" id="employeeAge"/>
                                </td> <td>
                                    <div id="employeeAgeError" style="display: none; color:red;"> Password's length must be between 8-15 symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td> COUNTRY </td>
                                <td><input type="text" name="country" value="" size="40" id="country"/>
                                </td> <td>
                                    <div id="countryError" style="display: none; color:red;"> Incorrect name of the country. </div>
                                </td>
                            </tr>
                            <tr> 
                                <td> CITY </td>
                                <td><input type="text" name="city" value="" size="40" id="city"/>
                                </td> <td>
                                    <div id="cityError" style="display: none; color:red;"> Incorrect name of the city. </div>
                                </td>
                            </tr>
                            <tr>
                                <td>GENDER</td>
                                <td>Male<input type="radio" name="gender" value="male" checked="checked" id="male"/>Female<input type="radio" name="gender" value="female" id="female" />
                                </td>
                            </tr>
                        </table>
                        <br>
                        <input type="submit" value="Add Employee" name="ConfirmData" />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
