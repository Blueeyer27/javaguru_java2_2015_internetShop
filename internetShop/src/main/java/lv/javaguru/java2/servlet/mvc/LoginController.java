package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.PasswordHash;
import lv.javaguru.java2.database.CartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.CartDB;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginController extends AccessController {
    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDAO;

    @Autowired
    @Qualifier("ORM_CartDAO")
    CartDAO cartDAO;

    @Override
    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession(true);

        if (request.getServletPath().equals("/logout"))
            return new MVCModel("/logout.jsp");

        if (request.getMethod().equals("POST")) {
            String error = null;
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            System.out.println("Login method: " + username + " " + password);

            if (username.isEmpty() || password.isEmpty())
                return new MVCModel("/login.jsp", "Login and password fields can't be empty.");


            User user = null;
            try {
                user = userDAO.getByLogin(username);
            } catch (DBException e) {
                //TODO: exception handling
                e.printStackTrace();
            }

            try {
                if (user == null)
                    error = "Incorrect login or/and password.";
                else if (!PasswordHash.validatePassword(password, user.getPassword()))
                    error = "Incorrect login or/and password.";
                else {
                    System.out.println("Login Successful!");

                    //session.removeAttribute("in_cart");

                    session.setAttribute("user_id", user.getId());
                    session.setAttribute("username", username);
                    session.setAttribute("name", user.getName());
                    session.setAttribute("surname", user.getSurname());
                    session.setAttribute("access_level", user.getAccessLevel());

                    Map<Long, Integer> inCart
                            = (HashMap<Long, Integer>) session.getAttribute("in_cart");

                    if (inCart.size() > 0) {
                        session.setAttribute("transfer", "transfer");
                        return new MVCModel("/transfer.jsp", "You have products in your cart. " +
                                "Do you want to transfer them on your account?");
                    } else {
                        loadCartToSession(session, user.getId());
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            return new MVCModel("/login.jsp", error);
        }

        return new MVCModel("/login.jsp", null);
    }

    private void loadCartToSession(HttpSession session, Long userID) {
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

        session.removeAttribute("in_cart");
        session.setAttribute("in_cart", sessionCart);
    }
}
