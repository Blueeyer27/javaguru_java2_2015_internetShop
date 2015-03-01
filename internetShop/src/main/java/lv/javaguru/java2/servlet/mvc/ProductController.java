package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
import java.io.File;
import java.util.List;

/**
 * Created by Anton on 2015.02.27..
 */

@Component
public class ProductController implements MVCController {

    private final String UPLOAD_DIRECTORY = "..\\internetShop\\src\\main\\webapp\\images\\products\\";

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

        System.out.println("Product ID: " + request.getParameter("id"));
        if(ServletFileUpload.isMultipartContent(request)){
            if (request.getMethod().equals("POST"))
                System.out.println("WOW");
            System.out.println(request.getParameter("info"));
            System.out.println(request.getAttribute("info"));
            System.out.println("IN upload state");
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        String name = new File(item.getName()).getName();
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                    } else {
                        String name = item.getFieldName();//text1
                        String value = item.getString();
                        request.setAttribute(name, value);
                    }
                }

                //File uploaded successfully
                System.out.println("File Uploaded Successfully");
                //TODO: update picture in DB
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                System.out.println("File Upload Failed due to " + ex);
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        }

        if (request.getParameter("id") != null)
            request.setAttribute("id", request.getParameter("id"));

        if (request.getAttribute("id") != null) {
            Long productID = Long.parseLong((String) request.getAttribute("id"));
            System.out.println(productID + "  In POST?");
            if (request.getMethod().equals("POST")) {
                System.out.println("In POST");
                if (request.getParameter("comment") != null) {
                    System.out.println("Comment button pressed!");
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
                } else if (request.getParameter("upload") != null) {
                    System.out.println("Upload button pressed!");
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

        return new MVCModel("/access.jsp", "Product ID need to be end as parameter.");
    }
}
