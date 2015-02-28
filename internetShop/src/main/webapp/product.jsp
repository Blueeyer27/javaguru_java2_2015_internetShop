<%@ page import="lv.javaguru.java2.domain.Product" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.27.
  Time: 22:50
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
        <% Product prod = (Product) request.getAttribute("model");
            if (prod != null) {%>

        <div id="column_w530">
            <div class="header_02"><%=prod.getName()%>
            </div>
            <div class="img-50">
                <% if (prod.getImage() != null) {%>
                <img src="<%=prod.getImage()%>">
                <% } else { %>
                <img src="images/products/NoImage.gif">
                <% } %>
            </div>
            <p class="em_text"><%=prod.getDescription()%>
            </p>

            <p><%="Price: " + prod.getPrice() + "\t"%>&nbsp;&nbsp;

                <%if (true) {%>
                <input id='<%=prod.getProductId()%>' type='submit' value='Put in Cart'
                       onclick='addProduct("<%=prod.getName()%>", "<%=prod.getProductId()%>")'>
                <% } else {%>
                <font color="#228b22">Product is in Cart.</font>
                <% }%>
            </p>

            <div class="margin_bottom_20"></div>
            <div class="cleaner"></div>
        </div>
        <br><br><br><br>

        <% } else {%>
        <p>Can't find product in database.</p>
        <% } %>

        <div id="column_w530">
            <%
                int ID = 5;
                String name = session.getAttribute("name") + " " + session.getAttribute("surname");
            %>
            <br>
            <table>
                <tr>Name: </tr>
                <tr><input name="name" value="<%=name%>" size="20" type="text"></tr>
                <br>
                <tr>Comment: </td><br>
                    <td><input style="height:100px;width:400px;font-size:14pt;" name="id" value="HERE WILL BE COMMENT BOX!" size="20" height="50" type="text"></td>
                </tr>
            </table>
        </div>
    </div>
</div>

</body>
</html>
