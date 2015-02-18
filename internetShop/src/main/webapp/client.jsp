<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.Client" %>
<%@ page import="lv.javaguru.java2.domain.Email" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.ClientsController" %>
<%@ page import="lv.javaguru.java2.domain.Phone" %>
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



        <table align="center" border="1" width="650">
            <tr>
                <td width="50"><b>ID</b></td>
                <td width="100"><b>Name</b></td>
                <td width="100"><b>Surname</b></td>
                <td width="150"><b>Personal Code</b></td>
                <td width="50"><b>Gender</b></td>
                <td width="100"><b>Email</b></td>
                <td width="100"><b>Phone</b></td>
            </tr>
            <%
                ClientsController.RezList rezList = (ClientsController.RezList) request.getAttribute("model");
                List<Client> clients = rezList.getClients();
                List<Email> emails = rezList.getEmails();
                List<Phone> phones = rezList.getPhones();
                for (Client c : clients ) {
            %>
            <tr>
                <td width="50"><%=c.getId()%></td>
                <td width="100"><%=c.getName()%></td>
                <td width="100"><%=c.getSurname()%></td>
                <td width="150"><%=c.getPersCode()%></td>
                <td width="50"><%=c.getGender()%></td>
                <td width="100">
                <% for (Email e : emails){
                        if (e.getClientId() == c.getId()){ %>
                            <%=e.getEmailAddr()%>
                       <% }
                    }
                %></td>

                <td width="100">
                    <% for (Phone p : phones){
                        if (p.getClientId() == c.getId()){ %>
                    <%=p.getPhoneNumber()%>
                    <% }
                    }
                    %></td>
            </tr>
            <%}%>

        </table>

        <h1 align="center">There are <%=clients.size()%> clients in the shop</h1>
    </body>
</html>
