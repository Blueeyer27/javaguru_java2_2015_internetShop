<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="lv.javaguru.java2.domain.Comment" %>
<%@ page import="java.util.List" %>
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
<script>
    function ShowOrHide(id) {
        var block = document.getElementById(id).style;
        block.display = (block.display == 'none') ? 'block' : 'none';
    }
</script>
<jsp:include page="includes/menu.jsp"/>
<%
    if(request.getMethod().equals("POST") && request.getAttribute("id") != null) {
        response.sendRedirect("/java2/product?id=" + request.getAttribute("id"));
    }
%>
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
                <img src="<%="/java2" + prod.getImage()%>">
                <% } else { %>
                <img src="images/products/NoImage.gif">
                <% } %>
            </div>
            <p class="em_text"><%=prod.getDescription()%>
            </p>

            <p><%="Price: " + prod.getPrice() + "\t"%>&nbsp;&nbsp;

                <%if (true) {%>
                <input id='<%=prod.getProductId()%>' type='submit' value='Put in Cart'
                       onclick=''>
                <% } else {%>
                <font color="#228b22">Product is in Cart.</font>
                <% }%>
            </p>
            <input id='upload' type='submit' value='Change Image'
                   onclick='ShowOrHide("upload_image")'>
            <div id="upload_image" style="display:none;">
                <h3> Choose File to Upload </h3>
                <form action="product" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="<%=prod.getProductId()%>">
                    <input type="file" name="file" multiple accept="image/*">
                    <input type="submit" name="upload" value="upload">
                </form>
                <div id="result">

                    <h3><font color="red"><%=request.getAttribute("message")%></font></h3>

                </div>
            </div>

            <div class="margin_bottom_20"></div>
            <div class="cleaner"></div>
        </div>
        <br><br><br><br>

        <% } else {%>
        <p>Can't find product in database.</p>
        <% } %>

        <%
            String username = (String) session.getAttribute("username");
        %>
        <div id="column_w530">
            <% if (username != null) {%>
            <form method="POST" action="product">
                <table>
                    <tr>
                        <td>Username:</td>
                        <td><%=username%></td>
                    </tr>
                    <tr>
                        <td>Comment:</td>
                        <td><input type="hidden" name="id" value="<%=prod.getProductId()%>"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input style="height:100px;width:400px;font-size:14pt;"
                                   type="text" name="comment"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="SUBMIT" value="Post comment" name="comment"></td>
                    </tr>
                </table>
            </form>
            <% } else {%>
            <p>Only registred users can post comments.</p>
            <% } %>
            <table>
                <% List<Comment> comments = (List<Comment>) request.getAttribute("all_comments");
                   if (comments != null) {
                       for(Comment comment : comments) {%>
                        <table>
                            <tr>
                                <td><%=comment.getUsername() + ": "%></td>
                                <td><%=comment.getComment()%></td>
                            </tr>
                        </table>
                <%     }
                   } else {%>
                    <p>Comment and be first.</p>
                <% } %>
            </table>
            <br><br><br>
        </div>
    </div>
</div>

</body>
</html>