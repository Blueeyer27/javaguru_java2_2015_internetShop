<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.IndexController" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="lv.javaguru.java2.AccessLevel" %>
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

    function productOperation(name, id, operation) {
        var uri = document.documentURI.split('?')[0];
        var page = getURLParameter("page");
        if (page == null) page = 1;

        switch (operation) {
            case "put" :
                window.alert("Product " + name + " added to your cart.");
                window.location = uri + "?page=" + page + "&cart=" + id;
                break;
            case "remove" :
                window.alert("Product " + name + " removed from your cart.");
                window.location = uri + "?page=" + page + "&remove=" + id;
                break;
            case "delete" :
                window.location = uri + "?page=" + page + "&delete=" + id;
                break;
            default :
                window.alert("Incorrect operation!");
        }
    }

    function createProduct() {
        window.location = "/java2/create";
    }

    function aboutProduct(id) {
        var uri = document.documentURI.split('?')[0];
        window.location = "product?id=" + id;
    }

    function getURLParameter(name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)
                || [,""])[1].replace(/\+/g, '%20')) || null
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
            if (products != null) { %>
        <div id="column_w530">
            <% if((Integer) session.getAttribute("access_level") == AccessLevel.ADMIN.getValue()) { %>
            <input type='submit' value='Add New Product'
                   onclick='ShowOrHide("test_div")'><br><br>
            <jsp:include page="includes/product_form.jsp"/>
            <% }%>
        <%
            int lastElem = 1;
            if (products.size() <= 10) lastElem = 0;

            for (int i = products.size() - 1; i >= lastElem; i--) {
            //for (Product prod : products) {
                Product prod = products.get(i);
                String picture = prod.getImage(); %>
            <div class="header_02"><%=prod.getName()%></div>
            <div class="img-50">
            <% if (picture != null) {%>
                    <img src="<%="/java2" + picture%>">
            <% } else { %>
                <img src="images/products/noimage.gif">
            <% } %>
            </div>
            <p class="em_text"><%=prod.getDescription()%></p>
            <p><%="Price: " + prod.getPrice() + "\t"%>&nbsp;&nbsp;
                <%--<%  int commentsCount = 0;--%>
                    <%--if (prod.getComments() != null) {--%>
                        <%--commentsCount = prod.getComments().size();--%>
                    <%--}%>--%>
                <%--<%="Commented " + commentsCount + " time(s)" + "\t"%>&nbsp;&nbsp;--%>
                <input id='<%=prod.getProductId()%>' type='submit' value='About'
                       onclick='aboutProduct("<%=prod.getProductId()%>")'>
                &nbsp;
                <input id='<%=prod.getProductId()%>' type='submit' value='Put in Cart'
                       onclick='productOperation("<%=prod.getName()%>", "<%=prod.getProductId()%>", "put")'>
                <%  if (inCart != null) {
                        if (inCart.containsKey(prod.getProductId())) {%>
                <input id='<%=prod.getProductId()%>' type='submit' value='Remove From Cart'
                       onclick='productOperation("<%=prod.getName()%>", "<%=prod.getProductId()%>", "remove")'>
                <br>
                <font color="#228b22">Count of this product in your cart : <%=inCart.get(prod.getProductId())%></font>
                <br><br>

                <%      }
                    } %>

                <% if((Integer) session.getAttribute("access_level") == AccessLevel.ADMIN.getValue())
                        { %>
                <input id='<%=prod.getProductId()%>' type='submit' value='Delete'
                       onclick='productOperation("<%=prod.getName()%>", "<%=prod.getProductId()%>", "delete")'>
                     <% }%>
            </p>
         <%  } %>
         <div class="margin_bottom_20"></div>
         <div class="cleaner"></div>
        </div><br><br><br><br><br><br><br><br><br><br>

        <div class="cleaner"></div>
        <%  if (products.size() > 10) { %>
        <input type='submit' value='Next Page'
               onclick='nextPage("<%=((IndexController.PageInfo)request.getAttribute("model")).getNextPageURI()%>")'/>
        <%  } %>
        <% } else {%>
        <p><font color="#006400">Page don't exist or product table in database is empty.</font></p>
        <% }%>
    </div>
</div>
</body>
</html>