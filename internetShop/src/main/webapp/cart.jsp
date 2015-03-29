<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.ProductInCart" %>
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
<script>
    function productInfo(id) {
        window.location = "product?id=" + id;
    }

    function removeFromCart(id, name) {
        window.alert("Product " + name + " removed from your cart.");
        window.location = "cart?remove_id=" + id;
    }
</script>
<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>

        <%  Double totalPrice = 0d;
            List<ProductInCart> inCart = (List<ProductInCart>) request.getAttribute("model");
            if (inCart.size() < 1) { %>
        <br><br><br>
        <p><font color="#228b22">Your cart is empty.</font></p>
        <%  } else {
                for (ProductInCart prod : inCart) {
                    Product product = prod.getProduct(); %>
        <h3><a onclick="productInfo('<%=product.getProductId()%>')"><%=product.getName()%></a></h3>
        <div class="img-25">
            <% if (product.getImage() != null) {%>
            <img src="<%="/java2" + product.getImage()%>">
            <% } else { %>
            <img src="images/products/noimage.gif">
            <% } %>
        </div>
        <p><%=product.getDescription() + " | Price for one: "
                + (double) product.getPrice()
                + "$ | Count: " + prod.getCount()%></p>
        <br>
        <input id='<%=product.getProductId()%>' type='submit' value='Remove From Cart'
               onclick='removeFromCart("<%=product.getProductId()%>", "<%=product.getName()%>")'>
        <br><br>
        <%  totalPrice+=((double) product.getPrice()*prod.getCount());
                }
            }%>
        <br><br>
        <p><b><h1><%="Total price = " + totalPrice + "$"%></h1></b></p>
    </div>
</div>
</body>
</html>
