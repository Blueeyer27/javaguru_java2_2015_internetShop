package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by Anton on 2015.03.15..
 */

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {
    @Mock
    private ProductDAO productDAO;

    @Mock
    private ProductInCartDAO productInCartDAO;

    @InjectMocks
    IndexController indexController = new IndexController();

    @Test
    public void putProductInGuestCart() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("27").when(request).getParameter("cart");
        doReturn(session).when(request).getSession();

        doReturn(new HashMap<Long, Integer>())
                .when(session).getAttribute("in_cart");

        doReturn(1).when(session).getAttribute("access_level");

        List<Product> products = createProducts(10);
        //Mockito.doReturn(products).when(productDAO).getRange(anyInt(), anyInt());
        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);
    }

    private List<Product> createProducts(int productCount) {
        List<Product> products = new ArrayList<Product>();
        for (int i = 1; i <= productCount; i++) {
            Product product = new Product("productName" + i,
                    "productDescription" + i, 15.99f);
            products.add(product);
        }

        return products;
    }

    @Test
    public void deleteProductFromDB() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);
        doReturn(session).when(request).getSession();

        doReturn("2727").when(request).getParameter("delete");


        //Mockito.doThrow(new Exception()).doNothing().when(productDAO).delete(anyLong());


        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);

        verify(productDAO, times(1)).delete(anyLong());
    }

    @Test
    public void addSameProdInUserCart() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("6").when(request).getParameter("cart");
        doReturn(session).when(request).getSession();

        doReturn(createSessionCart(10)).when(session).getAttribute("in_cart");
        doReturn(2).when(session).getAttribute("access_level");
        doReturn(27l).when(session).getAttribute("user_id");

        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);
    }

    @Test
    public void removeFromCart() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn(session).when(request).getSession();
        doReturn("4").when(request).getParameter("remove");
        doReturn("5").when(request).getParameter("page");

        //Map<Long, Integer> inCart = createSessionCart(10);
        doReturn(2).when(session).getAttribute("access_level");
        doReturn(createSessionCart(10)).when(session).getAttribute("in_cart");

        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);

        verify(productInCartDAO, times(1))
                .removeFromCart(any(Product.class), anyLong());
        verify(productDAO, times(1)).getById(anyLong());
    }

    //@Test(expected = DBException.class)
    @Test
    public void throwExceptionWhenRemoveProd() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn(session).when(request).getSession();
        doReturn("4").when(request).getParameter("remove");
        doReturn("5").when(request).getParameter("page");

        //Map<Long, Integer> inCart = createSessionCart(10);
        doReturn(2).when(session).getAttribute("access_level");
        doReturn(createSessionCart(10)).when(session).getAttribute("in_cart");
        doThrow(new DBException("Test exception!"))
                .when(productDAO).getById(anyLong());

//        doThrow(new DBException("Test exception!"))
//                .when(productDAO.getById(anyLong()));
                //.thenThrow(new DBException("Test exception!"));
        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);
    }

    @Test
    public void throwExceptionWhenDeleteProd() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn(session).when(request).getSession();
        doReturn("2727").when(request).getParameter("delete");
        doThrow(new DBException("Test exception!"))
                .when(productDAO).delete(anyLong());

        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);
    }

    @Test
    public void throwExceptionWhenGetProductByRange() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn(session).when(request).getSession();
        doThrow(new DBException("Test exception!"))
                .when(productDAO).getRange(anyInt(), anyInt());

        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);
    }

    @Test
    public void throwExceptionWhenPutInDBCart() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("6").when(request).getParameter("cart");
        doReturn(session).when(request).getSession();

        doReturn(createSessionCart(10)).when(session).getAttribute("in_cart");
        doReturn(2).when(session).getAttribute("access_level");
        doReturn(27l).when(session).getAttribute("user_id");

        doThrow(new DBException("Test exception!"))
                .when(productInCartDAO).addElem(any(ProductInCart.class));

        //indexController.safeRequest(request, null);
        indexController.processRequest(request, null);
    }

    private Map<Long, Integer> createSessionCart(int productCount) {
        Map<Long, Integer> inCart = new HashMap<Long, Integer>();

        for (long i = 1; i <= productCount; i++) {
            inCart.put(i, randomInt(1, 50));
        }

        return inCart;
    }

    private int randomInt(int min, int max) {
        return min + (int)(Math.random() * (max + 1));
    }
}
