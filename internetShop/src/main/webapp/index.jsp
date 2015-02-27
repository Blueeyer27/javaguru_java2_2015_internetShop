<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.IndexController" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.19.
  Time: 23:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="includes/header.jsp"/>
<body>
<script>
    function nextPage(link) {
        window.location = link;
    }

    function addProduct(name, id) {
        var uri = document.documentURI.split('?')[0];
        var page = getURLParameter("page");
        if (page == null) page = 1;

        window.alert("Product " + name + " added to your cart.");
        window.location = uri + "?page=" + page + "&cart=" + id;
    }

    function getURLParameter(name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
    }
</script>
<%
    Map<Long,Integer> inCart = (HashMap<Long,Integer>) session.getAttribute("in_cart");
%>
<jsp:include page="includes/menu.jsp"/>
<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>
        <%  List<Product> products = ((IndexController.PageInfo)request.getAttribute("model")).getProducts();
            if (products.size() < 1) {%>
        <p>Product table in database is empty.</p>
        <%  }
            for (Product prod : products) {
                String picture = prod.getImage(); %>
        <div id="column_w530">
            <div class="header_02"><%=prod.getName()%></div>
            <div class="img-50">
            <% if (picture != null) {%>
                    <img src="<%=prod.getImage()%>">
            <% } else { %>
                <img src="images/products/NoImage.gif">
            <% } %>
            </div>
            <p class="em_text"><%=prod.getDescription()%></p>
            <p><%="Price: " + prod.getPrice() + "\t"%>&nbsp;&nbsp;
                <%if (!inCart.containsKey(prod.getProductId())) {%>
                <input id='<%=prod.getProductId()%>' type='submit' value='Put in Cart'
                       onclick='addProduct("<%=prod.getName()%>", "<%=prod.getProductId()%>")'>
                <% } else {%>
                <font color="#228b22">Product is in Cart.</font>
                <% }%>
            </p>
            <div class="margin_bottom_20"></div>
            <div class="cleaner"></div>
        </div><br><br><br><br><br><br><br><br><br><br>
        <%  } %>
        <div class="cleaner"></div>
        <%  if (products.size() > 10) { %>
        <input type='submit' value='Next Page'
               onclick='nextPage("<%=((IndexController.PageInfo)request.getAttribute("model")).getNextPageURI()%>")'/>
        <%  } %>
    </div>
</div>
</body>
</html>