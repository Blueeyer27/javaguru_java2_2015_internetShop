package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.jdbc.ProductDAOImpl;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class CartController implements MVCController {

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();
        session.setAttribute("page_name", "Products in cart");

        List<Product> inCart = new ArrayList<Product>();

        if ((Integer)session.getAttribute("access_level") == AccessLevel.GUEST.getValue()) {
            Map<Long, Integer> products = (HashMap<Long, Integer>) session.getAttribute("in_cart");

            if (products.size() > 0) {
                ProductDAO productDAO = new ProductDAOImpl();
                for (Entry<Long, Integer> entry : products.entrySet()) {
                    Long id = entry.getKey();
                    Integer count = entry.getValue();

                    System.out.println("In cart: " + id);

                    try {
                        inCart.add(productDAO.getById(id));
                    } catch (DBException e) {
                        //if exception => product don't exist anymore or something wrong with DB
                        //TODO: logic if product don't exist anymore
                        e.printStackTrace();
                    }
                }
            }
        }

        return new MVCModel("/cart.jsp", inCart);
    }
}
