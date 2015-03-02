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
        <% } else {%>
        <h3>Here will be information about user.</h3>
        <% } %>
    </div>
</div>
</body>
</html>
