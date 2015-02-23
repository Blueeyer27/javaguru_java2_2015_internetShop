<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.IndexController" %>
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
</script>

<jsp:include page="includes/menu.jsp"/>

<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>

        <%  List<Product> products = ((IndexController.PageInfo)request.getAttribute("model")).getProducts();
            if (products.size() < 1) {%>
                <p>Product table in database is empty.</p>
            <%  }
            for (Product prod : products) { %>
        <div id="column_w530">
            <div class="header_02"><%=prod.getName()%></div>
            <p class="em_text"><%=prod.getDescription()%></p>
            <p><%="Price: " + prod.getPrice()%></p>
            <div class="margin_bottom_20"></div>
            <div class="cleaner"></div>
        </div>
        <%  } %>
        <div class="cleaner"></div>
        <%  if (products.size() > 10) {
            System.out.println("NextPageURI: " + ((IndexController.PageInfo)request.getAttribute("model")).getNextPageURI()); %>
        <INPUT TYPE='SUBMIT' VALUE='Next Page' ONCLICK='nextPage("<%=((IndexController.PageInfo)request.getAttribute("model")).getNextPageURI()%>")'/>
        <!--<form action="<%=((IndexController.PageInfo)request.getAttribute("model")).getNextPageURI()%>">
            <input type="submit" value="Next page">
        </form>-->
        <%  } %>

    </div>
</div>

</body>
</html>