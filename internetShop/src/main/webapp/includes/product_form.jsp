<%--
  Created by IntelliJ IDEA.
  User: Anton
  Date: 2015.03.07.
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function ShowOrHide(id) {
        var block = document.getElementById(id).style;
        block.display = (block.display == 'none') ? 'block' : 'none';
    }

</script>
<div id="test_div" style="display:none;">
    <form method="POST" action="add_product" enctype="multipart/form-data">
        <table>
            <tr>
                <td>Product name:</td>
                <td><input type="text" name="product_name"></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input type="text" name="description"></td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><input type="text" name="price"></td>
            </tr>
            <tr>
                <td>Picture:</td>
                <td><input type="file" name="file" multiple accept="image/*"></td>
            </tr><tr>
                <td></td>
                <td><input type="SUBMIT" value="Add product" name="submit"></td>
            </tr>
            <%--<% String error = (String) request.getAttribute("model");--%>
                <%--if (error != null) {%>--%>
            <%--<tr>--%>
                <%--<td></td>--%>
                <%--<td><font color="red"><%="Error: " + error%>--%>
                <%--</font></td>--%>

            <%--</tr>--%>
            <%--<% } %>--%>
        </table>
    </form>
</div>
