<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.NewItem" %>
<%@ page import="lv.javaguru.java2.AccessLevel" %>

<%--
  Created by IntelliJ IDEA.
  User: Anna
  Date: 27.02.15
  Time: 23:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="includes/header.jsp"/>
<body>

<script>
    function deleteItem(id) {
        var uri = document.documentURI.split('?')[0];
        window.location = "news?id=" + id;
    }
</script>

    <jsp:include page="includes/menu.jsp"/>
    <div id="content_wrapper">
        <div id="content">
            <jsp:include page="includes/user_bar.jsp"/>

            <!-- ------------GETTING NEWS FROM DB---------- -->
            <%  List<NewItem> news = (List<NewItem>) request.getAttribute("model");
                if (news.size() < 1) {%>
                    <p>No news</p>
            <%  }
                for (NewItem n : news) { %>
                    <div id="column_w530">
                        <div class="header_02"><%=n.getTitle()%></div>
                        <p class="em_text"><%=n.getBody()%></p>
                        <p><%=n.getDateID() + "\t"%></p>


                        <!-- ---------BUTTON FOR DELETING NEWS (ADMIN-VISIBLE)-------- -->
                        <% if ((Integer) session.getAttribute("access_level") > AccessLevel.GUEST.getValue()) {%>
                            <input id='<%=n.getDateID()%>' type='submit' value='delete'
                                onclick='deleteItem("<%=n.getDateID()%>")'>
                        <%}%>



                        <div class="margin_bottom_20"></div>
                        <div class="cleaner"></div>
                    </div><br><br><br><br><br><br><br><br><br><br>
            <%  } %>
            <div class="cleaner"></div>

            <!-- --------------------FORM FOR INSERTING NEWS (ADMIN-VISIBLE)------------------------ -->
            <% if ((Integer) session.getAttribute("access_level") > AccessLevel.GUEST.getValue()) {%>
                <form method="POST" action="news">
                    <table>
                        <tr>
                            <td>Title:</td>
                            <td><input type="text" name="title"></td>
                        </tr>
                        <tr>
                            <td>Body:</td>
                            <td><input type="text" name="body"></td>
                        </tr>

                        <tr>
                            <td></td>
                            <td><input type="SUBMIT" value="Add" name="submit"></td>
                        </tr>
                    </table>
                </form>
            <%}%>

        </div>
    </div>
</body>
</html>