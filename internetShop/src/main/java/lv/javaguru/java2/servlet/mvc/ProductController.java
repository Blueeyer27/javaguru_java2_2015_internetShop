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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 2015.02.27..
 */

//@Component
//public class ProductController extends AccessController {

@Controller
public class ProductController {
    private final String UPLOAD_DIRECTORY = "..\\internetShop\\src\\main\\webapp\\images\\products\\";

    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDAO;

    @Autowired
    @Qualifier("ORM_ProductDAO")
    private ProductDAO productDAO;

    @Autowired
    @Qualifier("ORM_CommentDAO")
    private CommentDAO commentDAO;

    private ServletFileUpload servletFileUpload =
            new ServletFileUpload(new DiskFileItemFactory());

//    @Override
//    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

    @RequestMapping(value = "product", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("product");

        HttpSession session = request.getSession();
        session.setAttribute("page_name", "Product Information");

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = servletFileUpload.parseRequest(request);

//                String fileName = null;
//                for (FileItem item : multiparts) {
//                    if (!item.isFormField()) {
//                        fileName = new File(item.getName()).getName();
//                        item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
//                    } else {
//                        String name = item.getFieldName();//text1
//                        String value = item.getString();
//                        request.setAttribute(name, value);
//                    }
//                }

                String fileName = uploadFileAndGetParams(request, multiparts);
                System.out.println("File Name: " + fileName);

                updateProductImage(Long.parseLong((String)request.getAttribute("id")), fileName);
                request.setAttribute("message", "Request successfully processed!");
            } catch (Exception ex) {
                System.out.println("File Upload Failed due to " + ex);
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        }

        if (request.getParameter("id") != null)
            request.setAttribute("id", request.getParameter("id"));

        if (request.getAttribute("id") != null) {
            Long productID = Long.parseLong((String) request.getAttribute("id"));

            if (request.getMethod().equals("POST")) {

                // If comment button pressed
                if (request.getParameter("comment") != null) {
                    try {
                        // Create new Comment if it's not empty
                        String comment = null;
                        if ((comment = request.getParameter("comment")) != null)
                            commentDAO.create(new Comment(
                                    userDAO.getById((Long) session.getAttribute("user_id")), //current user ID
                                    productID,
                                    comment));
                    } catch (DBException e) {
                        System.out.println("exception ;((");
                        //TODO: handle exception when can't add comment
                        e.printStackTrace();
                    }
//                    catch (ClassCastException e) {
//                        System.out.println("Incorrect user ID");
//                        e.printStackTrace();
//                    }
                }
            }

            //loadComments(request, productID);

            Product product = null;

            try {
                product = productDAO.getById(productID);
            } catch (DBException e) {
                e.printStackTrace();
            }

            if (product != null)
                loadComments(request, product);

            //return new MVCModel("/product.jsp", product);
            return model.addObject("model", product);
        }

        //return new MVCModel("/access.jsp", "Product ID need to be send as parameter.");
        model.setViewName("access");
        return model.addObject("model", "Product ID need to be send as parameter.");
    }

    private void updateProductImage(Long productID, String fileName) {
        Product product = null;
        //Get product from DB
        try {
            product = productDAO.getById(productID);
        } catch (DBException e) {
            e.printStackTrace();
            return;
        }

        product.setImage("/images/products/" + fileName);
        System.out.println(product.getImage());

        //Update product
        try {
            productDAO.update(product);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private String uploadFileAndGetParams(HttpServletRequest request, List<FileItem> multiparts) throws Exception {
        Map<String, String> params = new HashMap<String, String>();

        String fileName = null;

        for (FileItem item : multiparts) {
            if (!item.isFormField()) {
                fileName = new File(item.getName()).getName();
                item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
            } else {
                String name = item.getFieldName();//text1
                String value = item.getString();
                request.setAttribute(name, value);
            }
        }

        return fileName;
    }

//    private void loadComments(HttpServletRequest request, Long productID) {
//        List<Comment> comments = null;
//
//        try {
//            comments = commentDAO.getAll(productID);
//
//            //for (Comment comment : comments) {
//                //comment.setUsername(userDAO.getById(comment.getUserID()).getLogin());
//            //}
//        } catch (DBException e) {
//            e.printStackTrace();
//        }
//
//        request.setAttribute("all_comments", comments);
//    }


    private void loadComments(HttpServletRequest request, Product product) {
        List<Comment> comments = product.getComments();

        request.setAttribute("all_comments", comments);
    }
}
