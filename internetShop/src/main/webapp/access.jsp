<%@ page import="lv.javaguru.java2.AccessLevel" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.22.
  Time: 4:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="includes/header.jsp"/>
<% if ((Integer) session.getAttribute("access_level")
        != AccessLevel.BANNED.getValue()) {%>
<meta http-equiv="refresh" content="3;url=/java2/index"/>
<% } %>
<body>
<jsp:include page="includes/menu.jsp"/>

<div id="content_wrapper">
    <div id="content">
        <h1>Access Denied!</h1>

        <p>Reason: <%=request.getAttribute("model")%>
        </p>
        <% if ((Integer) session.getAttribute("access_level")
                != AccessLevel.BANNED.getValue()) {%>
        <p>Redirection in 3 seconds...</p>
        <% } %>
    </div>
</div>
</body>
</html>
