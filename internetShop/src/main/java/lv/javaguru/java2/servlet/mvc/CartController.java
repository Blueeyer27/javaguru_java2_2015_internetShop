package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.database.CartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.ProductInCart;
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
public class CartController extends AccessController {

    @Autowired
    @Qualifier("ORM_ProductDAO")
    private ProductDAO productDAO;

    @Autowired
    @Qualifier("ORM_CartDAO")
    private CartDAO cartDAO;

    @Override
    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();

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

                    Product product;

                    try {
                        product = productDAO.getById(id);

                        if (product != null)
                            inCart.add(new ProductInCart(product, count, false));
                        else
                            //Product don't exist in DB
                            toRemove.add(id);

                    } catch (DBException e) {
                        //TODO: logic if something wrong with DB
                        e.printStackTrace();
                    }
                }

                for (Long removeID : toRemove) {
                    products.remove(removeID);
                }
            }
        } else {
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
