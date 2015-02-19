package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.jdbc.ProductDAOImpl;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Anton on 2015.02.17..
 */
public class ProductsController implements MVCController  {
    class TwoLists {
        public TwoLists(List<Product> products, List<User> users) {

        }
    }

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        ProductDAO productDAO = new ProductDAOImpl();
        List<Product> products = null;

        try {
            products = productDAO.getAll();

            if (products.size() < 2) {
                productDAO.create(new Product("Apple", "Cheap and good fruit.", 0.25f));
                productDAO.create(new Product("Banana", "Description for banana.", 0.49f));
                productDAO.create(new Product("Tomato", "Description for tomato.", 0.99f));
                products = productDAO.getAll();
            }

        } catch (DBException ex) {

        }

        MVCModel model = new MVCModel("/products.jsp", products);

        return model;
    }
}
