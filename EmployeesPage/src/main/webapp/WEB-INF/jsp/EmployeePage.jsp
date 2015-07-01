<%-- 
    Document   : EmployeePage
    Created on : Jun 14, 2015, 1:14:32 PM
    Author     : tigran
--%>
<%@page import="db.functions.ParserFunctions"%>
<%@page import="db.functions.AccessTokens"%>
<%@page import="javax.persistence.Access"%>
<%@page import="db.functions.DBFunctions"%>
<%@page import="java.lang.String"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
            <%
                String employeeUsername = (String) session.getAttribute("userName");
                String employeePassword = (String) session.getAttribute("password");
                valiudateCredentials(employeeUsername, employeePassword, response);
                String accessToken = AccessTokens.getInstance().getEmployeeAccessToken(employeeUsername);
                //ParserFunctions parser = ParserFunctions.getInstance();
            %>

            <%!
                void valiudateCredentials(String username, String password, HttpServletResponse response) {
                    AccessTokens accesTokens = AccessTokens.getInstance();
                    System.out.println("username======" + username);
                    System.out.println("password======" + password);
                    accesTokens.printAccessTokens();
                    if (null == username || null == password
                            || null == accesTokens.getEmployeeAccessToken(username)) {
                        response.setStatus(response.SC_MOVED_TEMPORARILY);
                        response.setHeader("Location", "CredentialsRequired.htm");
                    }
                }
            %>

            function viewInbox() {
                document.getElementById("inboxDiv").style.display = "block";
                document.getElementById("sentDiv").style.display = "none";
                document.getElementById("sendMessage").style.display = "none";
                inboxMessagesResponse = sendRequest("GET", "employee/data/getinboxtable", "");
                if (inboxMessagesResponse.status != 200) {
                    window.location.assign("/EmployeesPage/CredentialsRequired.htm");
                } else {
                    inboxMessages = inboxMessagesResponse.responseText;
                    document.getElementById("inboxDiv").innerHTML =
                            "<h2>Inbox Messages</h2> <br>" + inboxMessages;
                }
            }

            function viewSent() {
                document.getElementById("inboxDiv").style.display = "none";
                document.getElementById("sentDiv").style.display = "block";
                document.getElementById("sendMessage").style.display = "none";
                sendMessagesResponse = sendRequest("GET", "employee/data/getsenttable", "");
                if (sendMessagesResponse.status != 200) {
                    window.location.assign("/EmployeesPage/CredentialsRequired.htm");
                } else {
                    sentMessages = sendMessagesResponse.responseText;
                    document.getElementById("sentDiv").innerHTML =
                            "<h2>Sent Messages</h2> <br>" + sentMessages;
                }
            }

            function writeMessage() {
                document.getElementById("inboxDiv").style.display = "none";
                document.getElementById("sentDiv").style.display = "none";
                document.getElementById("sendMessage").style.display = "block";
            }

            function OnSubmitSendMessage() {
                sendingMessage = {};
                sendingMessage["recipients"] = $('#recipientsID').val();
                sendingMessage["messageSubject"] = $('#subjectID').val();
                sendingMessage["messageText"] = $('#messageText').val();
                sendMessageResponse = sendRequest("POST", "employee/data/sendMessage", sendingMessage);
                sendMessageBodyText = JSON.parse(sendMessageResponse.responseText);
                alert(sendMessageBodyText.RequestStatus);
                //if(sendMessageResponse.status != 200)
            }

            function logOut() {
                logoutStatus = sendRequest("POST", "employee/data/logout", "").status;
                if (logoutStatus == 204) {
                    window.location.assign("/EmployeesPage/admin.htm");
                } else {
                    alert("Failed to logout of employee page.");
                }
            }

            function Test() {
                alert("in Test");
            }

            function ShowTheInboxMessage(messageId) {
                response = sendRequest("GET", "employee/data/getanhtmlinbox?id=" +
                        messageId, "");
                if (response.status == 200) {
                    document.getElementById("inboxDiv").innerHTML = response.responseText;
                } else {
                    window.location.assign("/EmployeesPage/CredentialsRequired.htm");
                }
            }
            
            function ShowTheSentMessage(messageId) {
                response = sendRequest("GET", "employee/data/getahtmlsent?id=" +
                        messageId, "");
                if (response.status == 200) {
                    document.getElementById("sentDiv").innerHTML = response.responseText;
                } else {
                    window.location.assign("/EmployeesPage/CredentialsRequired.htm");
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
                xmlhttp.setRequestHeader('Content-Type', 'application/json');
                xmlhttp.setRequestHeader('Authorization', 'FD aw4567123edkk99');
                xmlhttp.setRequestHeader('access-token', '<%= accessToken%>');
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

            function handleOnClose() {
                alert("In Close")
                logOut();
            }

        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee Page</title>
    </head>

    <body onunload="handleOnClose()">
        <div class="page"> 
            <div class="topDiv">
                <h2>Welcome to employee page!</h2>
            </div>
            <div class ="leftDiv">
                <input type="submit" value="Inbox" name="Inbox" onClick="viewInbox()" style="width:130px"/>
                <br>
                <input type="submit" value="Sent" name="Sent" onClick="viewSent()" style="width:130px;"/>
                <br>
                <input type="submit" value="Write Message" name="WriteMessage" onClick="writeMessage()"  style="width:130px;"/>
                <br>
                <input type="submit" value="Log Out" name="Log Out" onClick="logOut()"  style="width:130px;"/>
            </div>
            <div class="centerDiv" id="centralDiv">
                <table id="usPass">
                    <tr>
                        <td>UserName :</td><td>"<%= employeeUsername%>"</td>
                    </tr>
                    <tr>
                        <td>Password :</td><td>"<%= employeePassword%>"</td>
                    </tr>
                    <a href="javascript:Test()"> Test </a>
                </table>

                <div id="inboxDiv" style="display: none">
                    <h2>Inbox Messages</h2> 
                </div>

                <div id="sentDiv" style="display: none">
                    <h2>Sent Messages</h2> 
                </div>

                <div id="sendMessage" style="display: none">
                    <form name="sendMessage" method="post" action="javascript:OnSubmitSendMessage()">
                        <table>
                            <tr>
                                <td>
                                    Recipients
                                </td>
                                <td>
                                    <input type="text" id="recipientsID" name="recipientsName" value="" size="40" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Mail Subject
                                </td>
                                <td>
                                    <input type="text" id="subjectID" name="subjectName" value="" size="40" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Sending Message
                                </td>
                                <td>
                                    <textarea name="messageText" id="messageText" cols="50" rows="5">
                                    </textarea>
                                </td>
                            </tr>
                        </table>
                        <input type="submit" value="Send Message" name="Send Message" />  
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
