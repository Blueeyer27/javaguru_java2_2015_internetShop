<%@ page import="lv.javaguru.java2.domain.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.03.02.
  Time: 2:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="includes/header.jsp"/>
<body>
<jsp:include page="includes/menu.jsp"/>

<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>
        <% if (request.getAttribute("model") instanceof String) {%>
        <p><%=request.getAttribute("model")%>
        </p>
        <% } else if (request.getAttribute("model") instanceof User) {
            User user = (User) request.getAttribute("model");%>
        <b>
            <p>Name: <%=user.getName()%>
            </p><br>

            <p>Surname: <%=user.getSurname()%>
            </p><br>

            <p>Gender: <%=user.getGender()%>
            </p><br>

            <br><br>

            <p>Phone: <%=user.getPhone()%>
            </p><br>

            <p>Email: <%=user.getEmail()%>
            </p><br>

            <p>Login: <%=user.getLogin()%>
            </p><br>
        </b>
        <% } %>
    </div>
</div>
</body>
</html>
