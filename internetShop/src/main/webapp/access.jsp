<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.22.
  Time: 4:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=session.getAttribute("page_name")%></title>
    <meta http-equiv="refresh" content="3;url=/java2/index" />
</head>
<body>
<h1>Access Denied!</h1>
<p>Reason: <%=request.getAttribute("model")%></p>
<p>Redirection in 3 seconds...</p>
</body>
</html>
