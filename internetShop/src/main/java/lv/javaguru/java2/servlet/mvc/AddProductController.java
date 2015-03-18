package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 2015.03.07..
 */

//@Component
//public class AddProductController extends AccessController {

@Controller
public class AddProductController {
    private final String UPLOAD_DIRECTORY = "..\\internetShop\\src\\main\\webapp\\images\\products\\";

    @Autowired
    @Qualifier("ORM_ProductDAO")
    ProductDAO productDAO;

//    @Override
//    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

    @RequestMapping(value = "add_product", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        // While AccessController isn't in use setting page_name here.
        request.getSession().setAttribute("page_name", "Add Product");
        ModelAndView model = new ModelAndView("add_product");

        if (ServletFileUpload.isMultipartContent(request)) {
            Map<String, String> params = new HashMap<String, String>();
            Product product = new Product();

            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                params = getParamsMap(multiparts, product);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                if (Float.parseFloat(params.get("price")) <= 0.01f) {
                    //return new MVCModel("/add_product.jsp", "Product price can't be less than 0.01");
                    return model.addObject("model", "Product price can't be less than 0.01");
                }
            } catch (NumberFormatException ex) {
                //return new MVCModel("/add_product.jsp", "Incorrect price format. Example: \"0.25\"");
                return model.addObject("model", "Incorrect price format. Example: \"0.25\"");
            }

            setProductInfo(params, product);

            if (checkFields(product)) {
                //return new MVCModel("/add_product.jsp", "All fields must be filled.");
                return model.addObject("model", "All fields must be filled.");
            }
            try {
                productDAO.create(product);
            } catch (DBException e) {
                e.printStackTrace();
                //return new MVCModel("/add_product.jsp", "DB error. Try again.");
                return model.addObject("model", "DB error. Try again.");
            }
            //return new MVCModel("/add_product.jsp", "Success!");
            return model.addObject("model", "Success!");
        }

        //return new MVCModel("/add_product.jsp");
        return model;
    }

    private void setProductInfo(Map<String, String> params, Product product) {
        product.setDescription(params.get("description"));
        product.setName(params.get("product_name"));
        product.setPrice(Float.parseFloat(params.get("price")));
    }

    private Map<String, String> getParamsMap(List<FileItem> multiparts, Product product) throws Exception {
        Map<String, String> params = new HashMap<String, String>();

        for (FileItem item : multiparts) {
            if (!item.isFormField()) {
                // If upload photo request
                if (item.getName().length() > 0) {
                    String fileName = new File(item.getName()).getName();
                    item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
                    product.setImage("/images/products/" + fileName);
                }
            } else {
                params.put(item.getFieldName(),
                        item.getString());
                //System.out.println(item.getFieldName() + " : " + item.getString());
            }
        }

        return params;
    }

    private boolean checkFields(Product product) {
        //TODO: check for maximal size
        return (product.getDescription().isEmpty()
                || product.getName().isEmpty()
                || product.getPrice() <= 0);
    }
}
