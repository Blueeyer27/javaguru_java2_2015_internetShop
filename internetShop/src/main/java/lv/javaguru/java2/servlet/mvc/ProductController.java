package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.CommentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Comment;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Anton on 2015.02.27..
 */

@Component
public class ProductController implements MVCController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        //TODO: admin can edit

        HttpSession session = request.getSession();

        if (request.getParameter("id") != null) {
            Long productID = Long.parseLong(request.getParameter("id"));

            if (request.getMethod().equals("POST")) {

                try {
                    commentDAO.create(new Comment(
                            (Long) session.getAttribute("id"), //current user ID
                            productID,
                            request.getParameter("comment")
                    ));
                } catch (DBException e) {
                    System.out.println("exception ;((");
                    //TODO: handle exception when can't add comment
                    e.printStackTrace();
                }
            }

            List<Comment> comments = null;

            try {
                comments = commentDAO.getAll(productID);

                //TODO: add username in comments table and remove this....
                for (Comment comment : comments) {
                    comment.setUsername(userDAO.getById(comment.getUserID()).getLogin());
                }
            } catch (DBException e) {
                e.printStackTrace();
            }

            request.setAttribute("all_comments", comments);

            Product product = null;

            try {
                product = productDAO.getById(productID);
            } catch (DBException e) {
                e.printStackTrace();
            }

            return new MVCModel("/product.jsp", product);
        }
        return new MVCModel("/access.jsp");
    }
}
