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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IndexController extends AccessController {

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

    @Override
    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();

        if ((request.getParameter("delete") != null)) {
            Long prodID = Long.parseLong(request.getParameter("delete"));
            try {
                productDAO.delete(prodID);
            } catch (DBException e) {
                //TODO: make logic if failure
                e.printStackTrace();
            }
        }

        if (request.getParameter("remove") != null) {
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

        if (request.getParameter("cart") != null) {
            Long prodID = Long.parseLong(request.getParameter("cart"));

            putInSessionCart(prodID, session);

            if ((Integer) session.getAttribute("access_level")
                    > AccessLevel.GUEST.getValue()) {
                putInDBCart(prodID, session);
            }
        }

        List<Product> products = null;

        String page = request.getParameter("page");
        System.out.println("Requested page: " + page);
        if (page == null) page = "1";
        else if (Integer.parseInt(page) < 1) page = "1";

        try {
            products = productDAO.getRange((Integer.parseInt(page) - 1) * 10, 11);
            //products = productDAO.getAll();

            for (Product product : products)
                System.out.println(product.getDescription());

            System.out.println("GGSGS: " + products.get(0).getName());
            for (Product product : products)
                System.out.println(product.getName());

            // THIS IS JUST FOR TEST! (CREATE 100 NEW PRODUCTS IN DATABASE)
            if (productDAO.getTotal() < 1) {
                for (int i = 0; i < 100; i++) {
                    productDAO.create(new Product("product" + i, "This is test product description.", 0.25f));
                }
                products = productDAO.getRange((Integer.parseInt(page) - 1) * 10, 11);
            }

        } catch (DBException e) {
            e.printStackTrace();
        }

        String nextPage = (request.getRequestURI() + "?page=" +
                (Integer.parseInt(page) + 1));
        System.out.println("Next page: " + nextPage);

        return new MVCModel("/index.jsp", new PageInfo(products, nextPage));
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
    }
}
