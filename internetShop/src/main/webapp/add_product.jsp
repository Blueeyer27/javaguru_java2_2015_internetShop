<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.03.07.
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="includes/header.jsp"/>
<%  String error = null;
    if (request.getAttribute("model") != null
            && request.getAttribute("model") instanceof String)
        error = (String) request.getAttribute("model");
    if(error != null) {
        if (error.equals("Success!")){%>
<meta http-equiv="refresh" content="1;url=/java2/index" />
<%      }
    }%>
<body>
<jsp:include page="includes/menu.jsp"/>

<div id="content_wrapper">
    <div id="content" onfocus='ShowOrHide("test_div")'>
        <input type='submit' value='Add New Product'
               onclick='ShowOrHide("test_div")'><br><br>
        <jsp:include page="includes/user_bar.jsp"/>
        <jsp:include page="includes/product_form.jsp"/>
        <% if (error != null) {%>
        <tr>
        <td></td>
        <td><font color="red"><%="Error: " + error%>
        </font></td>

        </tr>
        <% } %>
    </div>
</div>

</body>
</html>
