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
import java.util.List;

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

        if (ServletFileUpload.isMultipartContent(request) && 2 == 1) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                String fileName = null;
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        fileName = new File(item.getName()).getName();
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
                    } else {
                        String name = item.getFieldName();
                        String value = item.getString();
                        request.setAttribute(name, value);
                    }
                }

                //File uploaded successfully
                System.out.println("File Uploaded Successfully");

                updateProductImage(Long.parseLong((String)request.getAttribute("id")), fileName);
                request.setAttribute("message", "File Uploaded Successfully");
            } catch (Exception ex) {
                System.out.println("File Upload Failed due to " + ex);
                request.setAttribute("message", "File Upload Failed due to " + ex);
            }

        }

        return new MVCModel("/add_product.jsp", "test");
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
}
