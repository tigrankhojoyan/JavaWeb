<%-- 
    Document   : example
    Created on : Mar 2, 2015, 10:55:43 PM
    Author     : tigran
--%>
<%@page import="java.lang.System"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>SportPage</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <script type="text/javascript">
            function displayRgistrationData() {
                document.getElementById("registrationDiv").style.display = "block";
            }


            <%
                List<String> englishClubs = (List<String>) session.getAttribute("England");
                List<String> spanishClubs = (List<String>) session.getAttribute("Spain");
                List<String> germanClubs = (List<String>) session.getAttribute("Germany");
                List<String> italianClubs = (List<String>) session.getAttribute("Italy");
                List<String> englishClubsSites = (List<String>) session.getAttribute("englishSites");
                List<String> spanishClubsSites = (List<String>) session.getAttribute("spanishSites");
                List<String> germanClubsSites = (List<String>) session.getAttribute("germanSites");
                List<String> italianClubsSites = (List<String>) session.getAttribute("italianSites");
                String loginStatus = (String) request.getAttribute("loginStatus");
                String fullName = (String) request.getAttribute("fullName");
                String registrationError = (String) request.getAttribute("RegistrationError");
            %>

            <%!
                String displayClabs(List<String> clubNames, List<String> websitesLocations) {
                    if (null != clubNames) {
                        String tableBody = "<ul>";
                        for (int i = 0; i < clubNames.size(); i++) {
                            tableBody += "<li><a href=\"" + websitesLocations.get(i)
                                    + "\" target=\"_blank\">"
                                    + clubNames.get(i) + "</a></li><br>";
                        }
                        tableBody += "</ul>";
                        return tableBody;
                    }
                    return "";
                }
            %>
            function displayLoginFailure()
            {
                loginStatus = "<%= loginStatus%>";
                fullName = "<%= fullName%>";
                if (loginStatus == "wrong") {
                    document.getElementById("loginFailed").style.display = "block";
                    document.getElementById("loginFailed").style.color = "red";
                } else if (loginStatus == "null") {
                    document.getElementById("loginFailed").style.display = "none";
                } else {
                    document.getElementById("signIn").style.display = "none";
                    document.getElementById("signOut").style.display = "block";
                    document.getElementById("loggedUserName").innerHTML = fullName;
                }
            }
            
            function displayRegistrationError() {
                registrationError= "<%= registrationError%>";
                if(registrationError != "null") {
                    alert(registrationError);
                    displayRgistrationData();
                }
            }

            function OnSubmitRegistrationForm()
            {
                username = document.getElementById("registrationUsername").value;
                password = document.getElementById("setPassword").value;
                firstName = document.getElementById("firstName").value;
                lastname = document.getElementById("lastName").value;
                country = document.getElementById("country").value;
                mailAdress = document.getElementById("email").value;
                city = document.getElementById("city").value;
                if(document.getElementById("male").checked) {
                    document.getElementById("female").value = "male";
                } else {
                    document.getElementById("male").value = "female";
                }
                
                reEnteredPass = document.getElementById("reEnterPass").value;
                /*alert(username + "<br>" + password + "<br>" + firstName +
                        "<br>" + lastname + "<br>" + country + "<br>" +
                        mailAdress + "<br>" + city + "<br>" + reEnteredPass);*/
                if(validateFirstName(firstName) && validateLastName(lastname) &&
                validateEmailAddress(mailAdress) && validatePassword(password) &&
                validateUsername(username) && validateCity(city) &&
                validateReEnteredPassword(reEnteredPass, password) &&
                validateCountry(country)) {
                    document.registrationForm.action ="Registration";
                    document.registrationForm.ConfirmData.click();
                }
                return true;
            }

            function validateFirstName(firstName) {
                var regExp = /^[A-Za-z]+$/;
                if (regExp.test(firstName)){
                    document.getElementById("firstNameError").style.display = "none"; 
                    return true;
                }
                else{
                    document.getElementById("firstNameError").style.display = "block"; 
                    return false;
                }
            }
            
            function validateLastName(lastName) {
                var regExp = /^[A-Za-z]+$/;
                if (regExp.test(lastName)){
                    document.getElementById("lastNameError").style.display = "none"; 
                    return true;
                }
                else{
                    document.getElementById("lastNameError").style.display = "block"; 
                    return false;
                }
            }
            
            function validateEmailAddress(emailAddress) {
                var regExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                if (regExp.test(emailAddress)) {
                    document.getElementById("emailAddressError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("emailAddressError").style.display = "block";
                    return true;
                }

            }
            
            function validatePassword(password) {
                if((password.length > 8) && (password.length < 15)) {
                    document.getElementById("passwordError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("passwordError").style.display = "block";
                    return false;
                }
            }
            
            function validateUsername(userName) {
                if((userName.length > 7) && (userName.length < 15)) {
                    document.getElementById("userNameError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("userNameError").style.display = "block";
                    return false;
                }
            }
            
            function validateReEnteredPassword(reEnteredPassword, password) {
                if(password == reEnteredPassword) {
                    document.getElementById("reEnteredPasswordError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("reEnteredPasswordError").style.display = "block";
                    return false;
                }
            }
            
            function validateCountry(country) {
                 var regExp = /^[A-Za-z]+$/;
                 if(regExp.test(country) && (country.length < 20)) {
                    document.getElementById("countryError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("countryError").style.display = "block";
                    return false;
                }
            }
            
            function validateCity(city) {
                 var regExp = /^[A-Za-z]+$/;
                 if(regExp.test(country) && (city.length < 20)) {
                    document.getElementById("cityError").style.display = "none";
                    return true;
                } else {
                    document.getElementById("cityError").style.display = "block";
                    return false;
                }
            }
            
        </script>
    </head>
    <body onload="displayLoginFailure();displayRegistrationError();">
        <div class="page">
            <div class="topDiv"> 
                <ul id="navlist">
                    <li><a href="" >England</a>
                        <% out.println(displayClabs(englishClubs, englishClubsSites)); %>
                    </li>
                    <li><a href="" > Spain </a>   
                        <% out.println(displayClabs(spanishClubs, spanishClubsSites)); %>
                    </li>
                    <li>
                        <a href="">
                            Germany
                        </a>
                        <% out.println(displayClabs(germanClubs, germanClubsSites)); %>
                    </li>
                    <li>
                        <a href="">
                            Italy
                        </a>
                        <% out.println(displayClabs(italianClubs, italianClubsSites));%>
                    </li>
                </ul>                   
                <form name="searchform" method="get" action="http://www.google.com/search" target="_blank" style="position:absolute; top:25px; right:5%;">
                    <input type="text" style="width:120px;height:25px" name="as_q" size="20"/>
                    <button type="submit">Google search</button> 
                </form>
                <a href="DatabaseFunctionalities" onclick="servletTest()">example </a>
            </div>
            <div class="leftDiv">
                <div id="signIn" style="font-size:0.5em"> 
                    <form name="Login" action="Signing" method="POST">
                        USER<input type="text" name="username" value="" size="15" />
                        PASS<input type="password" name="password" value="" size="15" />
                        <input type="submit" value="Sign In" name="signing" />
                        <div id="loginFailed" style="display:none"> 
                            Incorrect username or pass.
                            <br> Try again.
                        </div>
                    </form>
                    <br>
                    <input type="submit" value="Registration" name="Registration" onclick="displayRgistrationData()" />
                </div>
                <div id="signOut" style="display:none">
                    <div id="loggedUserName"></div>
                    <form name="signOutForm" action="DatabaseFunctionalities" method="POST">
                        <input type="submit" value="Sign Out" name="signout" />
                    </form>
                </div>
            </div>
            <div class="centerDiv">
                <div id="registrationDiv" style="display: none">
                    <form name="registrationForm" method="POST"  action="javascript:OnSubmitRegistrationForm()">
                        <table>
                            <tr>
                                <td> First Name </td>
                                <td> <input type="text" name="firstName" value="" size="40" id="firstName"/>
                                </td> <td> 
                                    <div id="firstNameError" style="display: none; color:red;"> First Name should contain only alpha symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td> Last Name </td>
                                <td> <input type="text" name="lastName" value="" size="40" id="lastName"/>
                                </td> <td>
                                    <div id="lastNameError" style="display: none; color:red;"> Last Name should contain only alpha symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td> Mail Address </td>
                                <td> <input type="text" name="email" value="" size="40" id="email"/>
                                </td> <td>
                                    <div id="emailAddressError" style="display: none; color:red;"> Invalid email address </div>
                                </td>
                            </tr>
                            <tr> 
                                <td> USERNAME </td> 
                                <td><input type="text" name="registrationUsername" value="" size="40" id="registrationUsername"/> 
                                </td> <td>
                                    <div id="userNameError" style="display: none; color:red;"> Username's length must be between 8-15 symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td> PASSWORD</td>
                                <td><input type="password" name="setPassword" value="" size="40" id="setPassword"/>
                                </td> <td>
                                    <div id="passwordError" style="display: none; color:red;"> Password's length must be between 8-15 symbols </div>
                                </td>
                            </tr>
                            <tr>
                                <td>PASSREENTER</td>
                                <td><input type="password" name="reEnterPass" value="" size="40" id="reEnterPass"/>
                                </td> <td>
                                    <div id="reEnteredPasswordError" style="display: none; color:red;"> Re-entered password's value must be equal to password's value. </div>
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
                        <input type="submit" value="sendData" name="ConfirmData" />
                    </form>

                </div>
                eee aber
                <br>
                <a href="example.jsp">eee aber</a>
                <%
                    out.println((String) request.getAttribute("test"));
                %>
            </div>
        </div>
    </body>
</html>

