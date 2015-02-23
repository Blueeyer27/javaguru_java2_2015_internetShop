<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.23.
  Time: 17:31
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

        <%  Double totalPrice = 0d;
            List<Product> inCart = (List<Product>) request.getAttribute("model");
            if (inCart.size() < 1) { %>
        <br><br><br>
        <p><font color="#228b22">Your cart is empty.</font></p>
        <%  } else {
                for (Product product : inCart) { %>
        <p><%=product.getName() + " | " + product.getDescription() + " | " + product.getPrice()%></p>
        <%  totalPrice+=product.getPrice();
                }
            }%>
        <br><br>
        <p><%="Total price = " + totalPrice%></p>
    </div>
</div>
</body>
</html>
