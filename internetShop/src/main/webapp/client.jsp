<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 11.02.15
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Client page</title>
    </head>

    <body>
        <h1 align="center">Hello from client JSP file!</h1>
        <h1 align="center">There are ${quantity} clients in the shop</h1>

        <table align="center" border="0" width="450">
            <tr>
                <td width="50"><b>ID</b></td>
                <td width="100"><b>Name</b></td>
                <td width="100"><b>Surname</b></td>
                <td width="150"><b>Personal Code</b></td>
                <td width="50"><b>Gender</b></td>
            </tr>
            <%Iterator itr;%>
            <% List data= (List)request.getAttribute("data");
                for (itr=data.iterator(); itr.hasNext(); ) {
            %>
            <tr>
                <td width="50"><%=itr.next()%></td>
                <td width="100"><%=itr.next()%></td>
                <td width="100"><%=itr.next()%></td>
                <td width="150"><%=itr.next()%></td>
                <td width="50"><%=itr.next()%></td>
            </tr>
            <%}%>
        </table>
    </body>
</html>
