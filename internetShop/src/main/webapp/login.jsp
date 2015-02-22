<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.22.
  Time: 5:13
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
            <div class="header_02">Login</div>

            <form method="POST" action="login">
                <table>
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
                        <td><input type="SUBMIT" value="Login" name="submit"></td>
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
