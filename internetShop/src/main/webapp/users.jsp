<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.User" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.UsersController" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Users</title>
    </head>

    <body>
        <h1 align="center">Statistics</h1>

        <table align="center" border="1" width="800">
            <tr>
                <td width="50"><b>ID</b></td>
                <td width="100"><b>Name</b></td>
                <td width="100"><b>Surname</b></td>
                <td width="50"><b>Gender</b></td>
                <td width="50"><b>Phone</b></td>
                <td width="50"><b>Email</b></td>
                <td width="50"><b>Login</b></td>
            </tr>
            <%
                UsersController.RezList rezList = (UsersController.RezList) request.getAttribute("model");
                List<User> users = rezList.getUsers();
                for (User u : users ) {
            %>
            <tr>
                <td width="50"><%=u.getId()%></td>
                <td width="100"><%=u.getName()%></td>
                <td width="100"><%=u.getSurname()%></td>
                <td width="50"><%=u.getGender()%></td>
                <td width="50"><%=u.getPhone()%></td>
                <td width="50"><%=u.getEmail()%></td>
                <td width="50"><%=u.getLogin()%></td>

            </tr>
            <%}%>

        </table>

        <h1 align="center">There are <%=users.size()%> users in the system of the shop</h1>
    </body>
</html>
