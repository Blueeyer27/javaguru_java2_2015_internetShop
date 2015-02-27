package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Anton on 2015.02.27..
 */

@Component
public class ProductController implements MVCController {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        //TODO: show chosen product (/product?id=103 (param - id))
        //TODO: admin can edit

        HttpSession session = request.getSession();

        if (request.getParameter("id") != null) {
            Long productID = Long.parseLong(request.getParameter("id"));
            Product product = null;

            try {
                product = productDAO.getById(productID);
            } catch (DBException e) {
                e.printStackTrace();
            }

            return new MVCModel("/product.jsp", product);
        }
        return new MVCModel("/product.jsp");
    }
}
