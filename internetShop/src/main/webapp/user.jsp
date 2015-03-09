<%@ page import="lv.javaguru.java2.domain.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.03.02.
  Time: 2:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="includes/header.jsp"/>
<script>
    function ShowOrHide(id) {
        var block = document.getElementById(id).style;
        block.display = (block.display == 'none') ? 'block' : 'none';
    }
</script>
<body>
<jsp:include page="includes/menu.jsp"/>

<div id="content_wrapper">
    <div id="content">
        <jsp:include page="includes/user_bar.jsp"/>
        <% if (request.getAttribute("message") instanceof String) {%>
        <h1><p style="color: darkred">
            <%="Error: " + request.getAttribute("message")%>
        </p></h1>
            <br><br><br>
        <% }
            if (request.getAttribute("model") instanceof User) {
            User user = (User) request.getAttribute("model");%>
        <b>
            <p>Name: <%=user.getName()%>
            </p><br>

            <p>Surname: <%=user.getSurname()%>
            </p><br>

            <p>Gender: <%=user.getGender()%>
            </p><br>

            <br><br>

            <p>Phone: <%=user.getPhone()%>
            </p><br>

            <p>Email: <%=user.getEmail()%>
            </p><br>

            <p>Login: <%=user.getLogin()%>
            </p><br><br><br><br>
            <p><h1>User Photo</h1></p><br>
            <% if (session.getAttribute("photo") != null) { %>
            <img src="<%="/java2" + session.getAttribute("photo")%>" alt="image"/>
            <% } else {%>
            <img src="/java2/images/users/nophoto.gif" alt="image"/>
            <% } %>
            <br><br>
            <input id='upload' type='submit' value='Change Profile Photo'
                   onclick='ShowOrHide("upload_image")'>
            <input id='update' type='submit' value='Update Information'
                   onclick='ShowOrHide("update_info")'>

            <div id="upload_image" style="display:none;">
                <h3> Choose File to Upload </h3>
                <form action="user" method="POST" enctype="multipart/form-data">
                    <input type="file" name="file" multiple accept="image/*">
                    <input type="submit" name="upload" value="upload">
                </form>
                <%--<div id="result">--%>

                <%--<h3><font color="red"><%=request.getAttribute("message")%></font></h3>--%>

                <%--</div>--%>
            </div>
            <br><br>
            <div id="update_info" style="display:none;">
                <form method="POST" action="user">
                    <table>
                        <tr>
                            <td>Name:</td>
                            <td><input type="text" name="name"
                                       value="<%=user.getName()%>"></td>
                        </tr>
                        <tr>
                            <td>Surname:</td>
                            <td><input type="text" name="surname"
                                       value="<%=user.getSurname()%>"></td>
                        </tr>
                        <tr>
                            <td>Gender:</td>
                            <td><input type="text" name="gender"
                                       value="<%=user.getGender()%>"></td>
                        </tr>
                        <tr>
                            <td>Phone:</td>
                            <td><input type="text" name="phone"
                                       value="<%=user.getPhone()%>"></td>
                        </tr>
                        <tr>
                            <td>Email:</td>
                            <td><input type="text" name="email"
                                       value="<%=user.getEmail()%>"></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="SUBMIT" value="Update" name="submit"></td>
                        </tr>
                    </table>
                </form>
            </div>
            </b>
            <% } %>
</body>
</html>
