package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.database.CartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.jdbc.ProductDAOImpl;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.servlet.mvc.MVCController;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("ORM_ProductDAO")
    private ProductDAO productDAO;

    @Autowired
    @Qualifier("ORM_CartDAO")
    private CartDAO cartDAO;

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();
        session.setAttribute("page_name", "Products in cart");

        List<ProductInCart> inCart = new ArrayList<ProductInCart>();

        if ((Integer)session.getAttribute("access_level") == AccessLevel.GUEST.getValue()) {
            Map<Long, Integer> products = (HashMap<Long, Integer>) session.getAttribute("in_cart");

            if (products.size() > 0) {
                // This array is fo ID of items which don't exist in DB anymore
                List<Long> toRemove = new ArrayList<Long>();

                for (Entry<Long, Integer> entry : products.entrySet()) {
                    Long id = entry.getKey();
                    Integer count = entry.getValue();

                    System.out.println("In cart: " + id);

                    Product product = null;
                    boolean deleted = false;

                    try {
                        product = productDAO.getById(id);

                        if (product != null)
                            inCart.add(new ProductInCart(product, count, false));
                        else
                            //Product don't exist in DB
                            toRemove.add(id);

                    } catch (DBException e) {
                        //if exception => product don't exist anymore or something wrong with DB
                        //TODO: logic if product don't exist anymore
                        e.printStackTrace();
                    }
                }

                for (Long removeID : toRemove) {
                    products.remove(removeID);
                }
            }
        } else {
            //TODO: don't forget about banned users
            Long userID = (Long) session.getAttribute("user_id");

            try {
                inCart = cartDAO.getCart(userID);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        return new MVCModel("/cart.jsp", inCart);
    }
}
