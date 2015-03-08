<%@ page import="lv.javaguru.java2.AccessLevel" %>
<%@ page import="java.util.HashMap" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.02.20.
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h3>
</h3>

<div id="column_w300">
    <div class="header_03">User</div>

    <div class="column_w300_section_01">
        <div class="news_image_wrapper">
            <% if ((Integer) session.getAttribute("access_level") == AccessLevel.GUEST.getValue()) {%>
            <img src="/java2/images/users/guest.png" alt="image"/>
            <% } else { %>
            <img src="/java2/images/users/nophoto.gif" alt="image"/>
            <% } %>
        </div>

        <div class="news_content">
            <div class="header_04"><a href="#">
                <% if ((Integer) session.getAttribute("access_level") == AccessLevel.GUEST.getValue()) {%>
                <%="Hello, Guest!"%>
            </a></div>
            <h3><a href="/java2/register" target="_self">Registration</a> |
                <a href="/java2/login" target="_self">Login</a><br>
                <a href="/java2/cart" target="_self"> My Cart:
                    (<%=((HashMap<Integer, Integer>)session.getAttribute("in_cart")).size()%>)</a></h3>
            <% } else {%>
            <%="Hello, " + session.getAttribute("name") + " "
                    + session.getAttribute("surname") + "!"%>
            </a></div>
        <h3><a href="/java2/logout" target="_self">Logout</a> |
            <a href="/java2/user" target="_self">Account</a><br>
            <a href="/java2/cart" target="_self">My Cart:
                (<%=((HashMap<Integer, Integer>)session.getAttribute("in_cart")).size()%>)</a></h3>
        <% } %>

    </div>

    <div class="cleaner"></div>
</div>


</div>