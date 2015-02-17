<%@ page import="lv.javaguru.java2.database.jdbc.ProductDAOImpl" %>
<%@ page import="lv.javaguru.java2.database.ProductDAO" %>
<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>JSP page</title>
</head>
<body>

    <h1>Products information</h1>
<%
    ProductDAO x = new ProductDAOImpl();
    List<Product> products = x.getAll();
    for (Product p : products) {
        out.print(p.getName() + " ");
    }
%>

</body>
</html>
