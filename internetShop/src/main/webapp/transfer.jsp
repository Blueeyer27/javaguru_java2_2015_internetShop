<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.03.08.
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="includes/header.jsp"/>
<script>
    function Transfer(answer) {
        window.location = "/java2/transfer?answer=" + answer;
    }
</script>
<% if (request.getAttribute("model")
        == null) {%>
<meta http-equiv="refresh" content="0.01;url=/java2/cart" />
<% } %>
<body>
<jsp:include page="includes/menu.jsp"/>

<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>
        <% String message = (String) request.getAttribute("model");
           if (message != null) {%>
            <p><font color="#006400"><%=message%></font></p>
        <input type='submit' value='Yes' onclick='Transfer("yes")'>
        <input type='submit' value='No' onclick='Transfer("no")'>
        <% } %>
    </div>
</div>

</body>
</html>
