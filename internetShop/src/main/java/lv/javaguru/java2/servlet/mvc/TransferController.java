package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.ProductInCart;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 2015.03.08..
 */

//@Component
//public class TransferController extends AccessController {

@Controller
public class TransferController {
    @Autowired
    @Qualifier("ORM_ProductInCartDAO")
    ProductInCartDAO productInCartDAO;

    @Autowired
    @Qualifier("ORM_ProductDAO")
    ProductDAO productDAO;

//    @Override
//    MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

    @RequestMapping(value = "transfer", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = AccessChecker.check(request);
        if (model != null) return model;

        model = new ModelAndView("transfer");

        //request.getSession().setAttribute("page_name", "Session Cart Transfer");
        if(request.getParameter("answer") != null) {
            HttpSession session = request.getSession();
            Long userID = (Long) session.getAttribute("user_id");

            String answer = request.getParameter("answer");

            if (answer.equals("yes")) {
                System.out.println("Answer is YES");
                Map<Long, Integer> sessionCart
                        = (Map<Long, Integer>) session.getAttribute("in_cart");

                for (Map.Entry<Long, Integer> entry : sessionCart.entrySet()) {
                    Long id = entry.getKey();
                    Integer count = entry.getValue();

                    try {
                        productInCartDAO.addElem(new ProductInCart(productDAO.getById(id),
                                userID, count, false));
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                }
            }

            session.removeAttribute("transfer");
            session.removeAttribute("in_cart");

            Map<Long, Integer> sessionCart = new HashMap<Long, Integer>();
            List<ProductInCart> cart = null;
            try {
                cart = productInCartDAO.getCart(userID);
            } catch (DBException e) {
                e.printStackTrace();
            }

            if (cart != null)
                for (ProductInCart elem : cart) {
                    if (!elem.getIsOrdered()
                        && !sessionCart.containsKey(elem.getProduct().getProductId())) {
                        sessionCart.put(elem.getProduct().getProductId(),
                                elem.getCount());
                    }
                }

            session.setAttribute("in_cart", sessionCart);
        }
        //return new MVCModel("/transfer.jsp", null);
        return model;
    }
}
