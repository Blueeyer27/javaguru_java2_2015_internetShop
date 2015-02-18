<%@ page import="lv.javaguru.java2.database.jdbc.ProductDAOImpl" %>
<%@ page import="lv.javaguru.java2.database.ProductDAO" %>
<%@ page import="lv.javaguru.java2.domain.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${pageContext.request.servletPath}</title>
    <link href="styles/design.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="container">
    <div id="title_bar">
        <div id="site_title">
            <h1>
                <a href="#">Shop<span>Best Internet Shop</span></a>
            </h1>
        </div>
        <div id="top_menu">
            <div class="contact_us"><a href="contacts.html"></a></div>
        </div>
    </div>

    <div id="banner_bar">

        <h2>JUST FOR TEST</h2>
        <p style="color:#000000">JUST FOR TESTJUST FOR TESTJUST FOR TESTJUST FOR TESTJUST FOR TESTJUST FOR TESTJUST FOR TEST</p>
        <div class="button_01 fr"><a href="#">Read...</a></div>

    </div>

    <div id="menu">
        <ul>
            <li><a href="#">Main</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contacts</a></li>
            <li><a href="#">Questions</a></li>
            <li><a class="current">Products</a></li>
        </ul>
    </div>

    <div id="content_osn">

        <div id="content">

            <div id="main_column">

                <div align="center">
                    <h3>Welcome!</h3>
                    <p>
                        <% List<Product> products = (List<Product>) request.getAttribute("model");
                            for (Product p : products) {
                        %>
                        <%=p.getName() + " - "%>
                        <%=p.getDescription()%>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <%=" Price: " + p.getPrice() + "\n"%>
                        <br>
                        <%}%></p><br><br>

                    <br><br><br><br>
                </div>

            </div>
        </div>
        <div class="cleaner"></div>
    </div>

    <div id="konec_bar_osn">
        <div id="konec_bar">
            <a href="#">Main</a>
            |   <a href="#">About</a>
            |	<a href="#">Contacts</a>
            |	<a href="#">Questions</a>
            |	Products<br><br>
        </div>
    </div>

</div>
</body>
</html>
