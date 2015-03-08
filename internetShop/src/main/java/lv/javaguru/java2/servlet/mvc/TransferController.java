package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.CartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.CartDB;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anton on 2015.03.08..
 */

@Component
public class TransferController extends AccessController {
    @Autowired
    @Qualifier("ORM_CartDAO")
    CartDAO cartDAO;

    @Override
    MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
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
                        cartDAO.addElem(new CartDB(id, userID, count, false));
                    } catch (DBException e) {
                        e.printStackTrace();
                    }
                }
            }

            session.removeAttribute("transfer");
            session.removeAttribute("in_cart");

            Map<Long, Integer> sessionCart = new HashMap<Long, Integer>();
            List<CartDB> cart = null;
            try {
                cart = cartDAO.getCart(userID);
            } catch (DBException e) {
                e.printStackTrace();
            }

            if (cart != null)
                for (CartDB elem : cart) {
                    if (!elem.getIsOrdered()
                        && !sessionCart.containsKey(elem.getProductId())) {
                        sessionCart.put(elem.getProductId(),
                                elem.getCount());
                    }
                }

            session.setAttribute("in_cart", sessionCart);
        }
        return new MVCModel("/transfer.jsp", null);
    }
}
