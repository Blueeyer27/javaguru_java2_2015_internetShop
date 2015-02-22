<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.22.
  Time: 2:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  if (((String) session.getAttribute("username")) != null)
    response.sendRedirect("/java2/index");
%>

<html>
<jsp:include page="includes/header.jsp"/>
<body>
<jsp:include page="includes/menu.jsp"/>
<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>

        <div id="column_w530">
            <div class="header_02">Registration</div>

            <form method="POST" action="register">
                <table>
                    <tr>
                        <td>Name:</td>
                        <td><input type="text" name="name"></td>
                    </tr>
                    <tr>
                        <td>Surname:</td>
                        <td><input type="text" name="surname"></td>
                    </tr>
                    <tr>
                        <td>Gender:</td>
                        <td><input type="text" name="gender"></td>
                    </tr>
                    <tr>
                        <td>Phone:</td>
                        <td><input type="text" name="phone"></td>
                    </tr>
                    <tr>
                        <td>Email:</td>
                        <td><input type="text" name="email"></td>
                    </tr>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username"></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="SUBMIT" value="Register" name="submit"></td>
                    </tr>
                    <% String error = (String) request.getAttribute("model");
                        if (error != null) {%>
                    <tr>
                        <td></td>
                        <td><font color="red"><%="Error: " + error%>
                        </font></td>

                    </tr>
                    <% } %>
                </table>
            </form>
            <div class="margin_bottom_20"></div>
            <div class="cleaner"></div>
        </div>

        <div class="cleaner"></div>
    </div>
</div>
</body>
</html>
