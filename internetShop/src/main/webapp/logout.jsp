<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.22.
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="refresh" content="3;url=/java2/index" />
    <title><%=session.getAttribute("page_name")%></title>
    <link href="styles/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="includes/menu.jsp"/>
<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>
        <% session.invalidate(); %>

        <div id="column_w530">
            <div class="header_02">Logout</div>
            <p>You have successfully logged out.<br>
                You will be redirected automatically in 3 seconds...</p>
            <div class="margin_bottom_20"></div>
            <div class="cleaner"></div>
        </div>

        <div class="cleaner"></div>
    </div>
</div>
</body>
</html>
