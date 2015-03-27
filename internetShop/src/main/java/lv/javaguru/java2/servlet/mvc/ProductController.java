package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.ProductInCart;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    @Qualifier("ORM_ProductInCartDAO")
    private ProductInCartDAO productInCartDAO;

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

                String fileName = uploadFileAndGetParams(request, multiparts);

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

            if (request.getMethod().equals("POST"))
                // If comment button pressed
                if (request.getParameter("comment") != null)
                    createComment(productID, request);

            Product product = null;

            try {
                product = productDAO.getById(productID);
            } catch (DBException e) {
                e.printStackTrace();
            }

            if (request.getParameter("remove") != null) {
                removeProduct(request);
            }

            if (request.getParameter("cart") != null) {
                Long prodID = Long.parseLong(request.getParameter("cart"));
                putInSessionCart(prodID, session);
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

    private void createComment(Long productID, HttpServletRequest request) {
        try {
            // Create new Comment if it's not empty
            String comment = null;
            if ((comment = request.getParameter("comment")) != null)
                commentDAO.create(new Comment(
                        userDAO.getById((Long) request.getSession()
                                .getAttribute("user_id")), //current user ID
                        productID,
                        comment));
        } catch (DBException e) {
            System.out.println("exception ;((");
            //TODO: handle exception when can't add comment
            e.printStackTrace();
        }
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

        boolean isUploaded = false;
        for (FileItem item : multiparts) {
            if (!item.isFormField()) {
                if (isUploaded) continue;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyhhmmss");
                fileName = new File(simpleDateFormat.format(new Date())
                        + item.getName()).getName();

                System.out.println(fileName + ":::" + item.getName() + ":::"
                        + simpleDateFormat.format(new Date()));
                item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
                isUploaded = true;
            } else {
                String name = item.getFieldName();//text1
                String value = item.getString();
                request.setAttribute(name, value);
            }
        }

        return fileName;
    }

    private void loadComments(HttpServletRequest request, Product product) {
        List<Comment> comments = product.getComments();

        request.setAttribute("all_comments", comments);
    }


    private void putInDBCart(Long prodID, HttpSession session) {
        Long userID = (Long) session.getAttribute("user_id");
        try {
            productInCartDAO.addElem(
                    new ProductInCart(productDAO.getById(prodID), userID, 1, false));
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void putInSessionCart(Long prodID, HttpSession session) {
        Map<Long, Integer> inCart = (HashMap<Long, Integer>) session.getAttribute("in_cart");
        if (!inCart.containsKey(prodID)) {
            inCart.put(prodID, 1);
        } else {
            Integer count = inCart.get(prodID);
            count++;
            inCart.put(prodID, count);
            System.out.println("New count of product: " + count);
        }

        if ((Integer) session.getAttribute("access_level")
                > AccessLevel.GUEST.getValue()) {
            putInDBCart(prodID, session);
        }
    }

    private void removeProduct(HttpServletRequest request) {
        Long prodID = Long.parseLong(request.getParameter("remove"));
        HttpSession session = request.getSession();

        Map<Long, Integer> inCart = (HashMap<Long, Integer>) session.getAttribute("in_cart");
        if (inCart.containsKey(prodID)) {
            inCart.remove(prodID);

            if ((Integer) session.getAttribute("access_level")
                    > AccessLevel.GUEST.getValue()) {
                Long userID = (Long) session.getAttribute("user_id");
                try {
                    productInCartDAO.removeFromCart(productDAO.getById(prodID), userID);
                } catch (DBException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
