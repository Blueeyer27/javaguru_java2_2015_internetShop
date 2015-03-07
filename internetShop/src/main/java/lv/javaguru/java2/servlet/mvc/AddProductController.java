package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessCheck;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 2015.03.07..
 */

@Component
public class AddProductController extends AccessCheck implements MVCController {

    private final String UPLOAD_DIRECTORY = "..\\internetShop\\src\\main\\webapp\\images\\products\\";

    @Autowired
    @Qualifier("ORM_ProductDAO")
    ProductDAO productDAO;

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

        if (ServletFileUpload.isMultipartContent(request)) {
            Map<String, String> params = new HashMap<String, String>();

            Product product = new Product();

            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                String fileName = null;
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        if (item.getName().length() > 0) {
                            fileName = new File(item.getName()).getName();
                            item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
                            product.setImage("/images/products/" + fileName);
                        }
                    } else {
                        params.put(item.getFieldName(),
                                item.getString());

                        System.out.println(item.getFieldName() + " : " + item.getString());
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                if (Float.parseFloat(params.get("price")) <= 0.01f)
                    return new MVCModel("/add_product.jsp", "Product price can't be less than 0.01");
            } catch (NumberFormatException ex) {
                return new MVCModel("/add_product.jsp", "Incorrect price format. Example: \"0.25\"");
            }

            product.setDescription(params.get("description"));
            product.setName(params.get("product_name"));
            product.setPrice(Float.parseFloat(params.get("price")));

            if (checkFields(product))
                return new MVCModel("/add_product.jsp", "All fields must be filled.");

            try {
                productDAO.create(product);
            } catch (DBException e) {
                e.printStackTrace();
                return new MVCModel("/add_product.jsp", "DB error. Try again.");
            }
            return new MVCModel("/add_product.jsp", "Success!");
        }

        return new MVCModel("/add_product.jsp");
    }

    private boolean checkFields(Product product) {
        //TODO: check for maximal size
        return (product.getDescription().isEmpty()
                || product.getName().isEmpty()
                || product.getPrice() <= 0);
    }
}
