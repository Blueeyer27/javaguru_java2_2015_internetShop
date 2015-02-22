package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.jdbc.ProductDAOImpl;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Anton on 2015.02.22..
 */
public class IndexController implements MVCController {
    public class PageInfo {
        private List<Product> _products;
        private String _nextPage;

        public List<Product> getProducts() { return _products; }
        public String getNextPageURI() { return _nextPage; }

        public PageInfo(List<Product> products, String nextPageURI) {
            _products = products;
            _nextPage = nextPageURI;
        }
    }

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();
        session.setAttribute("page_name", "Home page");

        if ((Integer) session.getAttribute("access_level") == AccessLevel.BANNED.getValue())
            return new MVCModel("/access.jsp", "You have been banned. " +
                    "\nContact site administrator for more information." +
                    "\nexample@gmail.com");

        ProductDAO productDAO = new ProductDAOImpl();
        List<Product> products = null;

        String page = request.getParameter("page");
        System.out.println("Requested page: " + page);
        if (page == null) page = "1";
        else if (Integer.parseInt(page) < 1) page = "1";

        try {
            products = productDAO.getRange((Integer.parseInt(page) - 1)*10, 11);

            // THIS IS JUST FOR TEST! (CREATE 100 NEW PRODUCTS IN DATABASE)
            //if (products.size() < 10) {
            //    for (int i = 0; i < 100; i++) {
            //        productDAO.create(new Product("product" + i, "This is test product description.", 0.25f));
            //    }
            //    products = productDAO.getRange((Integer.parseInt(page) - 1)*10, 11);
            //}

        } catch (DBException e) {
            e.printStackTrace();
        }

        for (Product p : products) {
            System.out.println(p.getName());
        }

        System.out.println("URL: " + request.getRequestURL());
        System.out.println("Header: " + request.getHeader("referer"));
        System.out.println("Context: " + request.getContextPath());
        String nextPage = (request.getRequestURI() + "?page=" +
                (Integer.parseInt(page) + 1));
        System.out.println("URI: " + nextPage);

        return new MVCModel("/index.jsp", new PageInfo(products, nextPage));
    }
}
