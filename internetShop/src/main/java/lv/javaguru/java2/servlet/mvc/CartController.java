package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.AccessCheck.AccessChecker;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//@Component
//public class CartController extends AccessController {
@Controller
public class CartController {

    @Autowired
    @Qualifier("ORM_ProductDAO")
    private ProductDAO productDAO;

    @Autowired
    @Qualifier("ORM_ProductInCartDAO")
    private ProductInCartDAO productInCartDAO;

    private HttpSession session;

//    @Override
//    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

    @RequestMapping(value = "cart", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = AccessChecker.check(request);
        if (model != null) return model;

        model = new ModelAndView("cart");

        session = request.getSession();
        //session.setAttribute("page_name", "My Cart");

        List<ProductInCart> inCart = new ArrayList<ProductInCart>();

        if (request.getParameter("remove_id") != null) {
            removeFromCart(request);
        }

        if ((Integer)session.getAttribute("access_level")
                == AccessLevel.GUEST.getValue()) {
            getGuestCart(inCart, request);
        } else {
            Long userID = (Long) session.getAttribute("user_id");

            try {
                inCart = productInCartDAO.getCartWithProd(userID);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        //return new MVCModel("/cart.jsp", inCart);
        return model.addObject("model", inCart);
    }

    private void getGuestCart(List<ProductInCart> inCart, HttpServletRequest request) {
        Map<Long, Integer> products = (HashMap<Long, Integer>) session.getAttribute("in_cart");

        if (products == null) return;

        if (products.size() > 0) {
            // This array is fo ID of items which don't exist in DB anymore
            List<Long> toRemove = new ArrayList<Long>();

            for (Entry<Long, Integer> entry : products.entrySet()) {
                Long id = entry.getKey();
                Integer count = entry.getValue();

                try {
                    Product product = productDAO.getById(id);
//                    System.out.println("ID " + id);
//
//                    if (product != null)
//                        System.out.println("Name: " + product.getName());

                    if (product != null)
                        inCart.add(new ProductInCart(product, count, false));
                    else toRemove.add(id); //product don't exist in DB anymore

                } catch (DBException e) {
                    //TODO: logic if something wrong with DB
                    e.printStackTrace();
                }
            }

            for (Long removeID : toRemove) products.remove(removeID);
        }
    }

    private void removeFromCart(HttpServletRequest request) {
        Long prodID = Long.parseLong(request.getParameter("remove_id"));

        Map<Long, Integer> inSessionCart
                = (HashMap<Long, Integer>) session.getAttribute("in_cart");

        if (inSessionCart == null) return;

        if (inSessionCart.containsKey(prodID)) {
            boolean error = false;
            //inSessionCart.remove(prodID);

            if ((Integer) session.getAttribute("access_level")
                    > AccessLevel.GUEST.getValue()) {
                Long userID = (Long) session.getAttribute("user_id");
                try {
                    productInCartDAO.removeFromCart(productDAO.getById(prodID), userID);
                } catch (DBException e) {
                    error = true;
                    e.printStackTrace();
                }
            }

            if (!error)
                inSessionCart.remove(prodID);
        }
    }
}
