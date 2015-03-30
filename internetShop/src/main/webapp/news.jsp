<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.NewItem" %>
<%@ page import="lv.javaguru.java2.AccessLevel" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="lv.javaguru.java2.servlet.mvc.NewsController" %>
<%@ page import="lv.javaguru.java2.domain.Category" %>
<%@ page import="java.util.Collection" %>

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
    function removeItem(id, category) {
        var uri = document.documentURI.split('?')[0];
        window.location = "news?idView=" + category + "&idRemove=" + id;
    }

    function likeItem(id, category) {
        var uri = document.documentURI.split('?')[0];
        window.location = "news?idView=" + category + "&idLike=" + id;
    }

    function view(name) {
        var uri = document.documentURI.split('?')[0];
        window.location = "news?idView=" + name.value;
    }

    function order(param) {
        var uri = document.documentURI.split('?')[0];
        window.location = "news?idParam=" + param.value;
    }

    function orderCat(param, name) {
        var uri = document.documentURI.split('?')[0];
        window.location = "news?idView=" + name + "&idParam=" + param.value;
    }

</script>

<% ArrayList<Long> likedItems = (ArrayList<Long>) session.getAttribute("liked"); %>

<%List<Category> categories = ((NewsController.Result) request.getAttribute("model")).getCategories();
    //List<NewItem> news = (List<NewItem>) request.getAttribute("model");%>

<% List<NewItem> news = ((NewsController.Result) request.getAttribute("model")).getNews();%>

<jsp:include page="includes/menu.jsp"/>
<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>


        <!-- --------------------FORM FOR INSERTING NEWS (ADMIN-VISIBLE)------------------------ -->
        <% if ((Integer) session.getAttribute("access_level") > AccessLevel.GUEST.getValue()) {%>
        <form method="POST" action="news">
            <font size="6" style="color:blue">Add a new here!</font>
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
                    <td>Category:</td>
                    <td>
                        <%for (Category ct : categories){%>
                        <input type="radio" name="category" value=<%=ct.getCatName()%> checked>
                        <%=ct.getCatName()%><br>
                        <%}%>

                    </td>

                </tr>

                <tr>
                    <td></td>
                    <td><input type="SUBMIT" value="Add" name="submit"></td>
                </tr>
            </table>
        </form>
        <%}%>

        <!--------------GETTING NEWS FROM DB---------- -->

        <td>Choose category:</td>
        <select name="selectCat" onchange="view(this)">
            <option value=""></option>
            <option value="All">All</option>
            <% for (Category ca : categories) {%>
            <option value=<%=ca.getCatName() %> ><%=ca.getCatName() %></option>
            <%}%>
        </select><br><br><br>



        <% List<NewItem> newsFromCat = ((NewsController.Result) request.getAttribute("model")).getNewsFromCategory();
            String catTitle = new String("All");
            if(newsFromCat.size() < news.size()){
                catTitle = newsFromCat.get(0).getCategory().getCatName();
            }

            if (newsFromCat.size() > 0){
                //String catTitle = newsFromCat.get(0).getCategory().getCatName();%>

        <td>Choose order:</td>
        <select name="selectOrder" onchange='orderCat(this, "<%=catTitle%>")'>
            <option value=""></option>
            <option value="first" >From old to new</option>
            <option value="last" >From new to old</option>
            <!--option value="popular" >Popular</option-->
        </select><br><br><br>

        <div id="column_w530"><div class="header_03">Category: <%=catTitle%></div><br><br></div>
        <%for (NewItem n : newsFromCat){%>
        <div id="column_w530"><div class="header_02"><%=n.getTitle()%></div>
            <p class="em_text"><%=n.getBody()%></p>
            <p><%=n.getLikes()%> people like this</p>
            <p><%=n.getDateID() + "\t"%></p>


            <!-- ---------BUTTON FOR DELETING NEWS (ADMIN-VISIBLE)-------- -->
            <% if ((Integer) session.getAttribute("access_level") > AccessLevel.GUEST.getValue()) {%>
            <input id='<%=n.getNum()%>' type='submit' value='Move to archive'
                   onclick='removeItem("<%=n.getNum()%>", "<%=n.getCategory().getCatName()%>")'>
            <%}%>

            <!-- ----------------------BUTTON FOR LIKES------------------- -->
            <% if ((Integer) session.getAttribute("access_level") < AccessLevel.ADMIN.getValue()) {%>
            <% if(!likedItems.contains(n.getNum())){%>
            <input id='<%=n.getNum()%>' type='submit' value='Like'
                   onclick='likeItem("<%=n.getNum()%>", "<%=n.getCategory().getCatName()%>")'>
            <%} else {%>
            <font color="#228b22"></font>
            <% }%>
            <%}%>

            <div class="margin_bottom_20"></div>
            <div class="cleaner"></div>
        </div><br><br><br><br><br><br><br><br><br><br>
        <%  } %>
        <div class="cleaner"></div>

        <% } %>


    </div>
</div>
</body>
</html>