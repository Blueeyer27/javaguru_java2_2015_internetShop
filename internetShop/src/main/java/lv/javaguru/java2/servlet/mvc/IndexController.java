package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.domain.Product;
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

//@Component
//public class IndexController extends AccessController {

@Controller
public class IndexController {

    @Autowired
    @Qualifier("ORM_ProductDAO")
    private ProductDAO productDAO;

    @Autowired
    @Qualifier("ORM_ProductInCartDAO")
    private ProductInCartDAO productInCartDAO;

    public class PageInfo {
        private List<Product> _products;
        private String _nextPage;

        public List<Product> getProducts() {
            return _products;
        }

        public String getNextPageURI() {
            return _nextPage;
        }

        public PageInfo(List<Product> products, String nextPageURI) {
            _products = products;
            _nextPage = nextPageURI;
        }
    }

    HttpSession session;


//    @Override
//    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

    @RequestMapping(value = "index", method = {RequestMethod.GET})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        session = request.getSession();

        if ((request.getParameter("delete") != null)) {
            deleteProduct(request);
        }

        if (request.getParameter("remove") != null) {
            removeProduct(request);
        }

        if (request.getParameter("cart") != null) {
            Long prodID = Long.parseLong(request.getParameter("cart"));
            putInSessionCart(prodID, session);
        }

        List<Product> products = null;

        String page = request.getParameter("page");

        if (page == null) page = "1";
        else if (Integer.parseInt(page) < 1) page = "1";

        try {
            products = productDAO.getRange((Integer.parseInt(page) - 1) * 10, 11);
        } catch (DBException e) {
            e.printStackTrace();
        }

        String nextPage = (request.getRequestURI() + "?page=" +
                (Integer.parseInt(page) + 1));

        //return new MVCModel("/index.jsp", new PageInfo(products, nextPage));
        return model.addObject("model", new PageInfo(products, nextPage));
    }

    private void removeProduct(HttpServletRequest request) {
        Long prodID = Long.parseLong(request.getParameter("remove"));

        Map<Long, Integer> inCart = (HashMap<Long, Integer>) session.getAttribute("in_cart");
        if (inCart.containsKey(prodID)) {
            inCart.remove(prodID);

            if ((Integer) session.getAttribute("access_level")
                    > AccessLevel.GUEST.getValue()) {
                Long userID = (Long) session.getAttribute("user_id");
                try {
                    productInCartDAO.removeFromCart(productDAO.getById(prodID), userID);
                } catch (DBException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void deleteProduct(HttpServletRequest request) {
        Long prodID = Long.parseLong(request.getParameter("delete"));
        try {
            productDAO.delete(prodID);
        } catch (DBException e) {
            //TODO: make logic if failure
            e.printStackTrace();
        }
    }

    private void putInDBCart(Long prodID, HttpSession session) {
        Long userID = (Long) session.getAttribute("user_id");
        try {
            productInCartDAO.addElem(new ProductInCart(productDAO.getById(prodID), userID, 1, false));
            System.out.println("PRODUCT ADDED TO CART");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private void putInSessionCart(Long prodID, HttpSession session) {
        Map<Long, Integer> inCart = (HashMap<Long, Integer>) session.getAttribute("in_cart");
        if (!inCart.containsKey(prodID)) {
            inCart.put(prodID, 1);
        } else {
            Integer count = inCart.get(prodID);
            count++;
            inCart.put(prodID, count);
            System.out.println("New count of product: " + count);
        }

        if ((Integer) session.getAttribute("access_level")
                > AccessLevel.GUEST.getValue()) {
            putInDBCart(prodID, session);
        }
    }
}
