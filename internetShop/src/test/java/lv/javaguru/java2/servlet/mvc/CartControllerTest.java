package lv.javaguru.java2.servlet.mvc;

import junit.framework.TestCase;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.ProductInCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Anton on 2015.03.20..
 */

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest extends TestCase {

    @Mock
    private ProductDAO productDAO;

    @Mock
    private ProductInCartDAO productInCartDAO;

    @InjectMocks
    CartController cartController = new CartController();

    Map<Long, Integer> sessionCart;

    @Test
    public void guestRemoveWhenCartNullTest() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("27").when(request).getParameter("remove_id");
        doReturn(session).when(request).getSession();
        doReturn(1).when(session).getAttribute("access_level");

        cartController.processRequest(request, null);

        verify(productDAO, times(0)).getById(anyLong());
        verify(productInCartDAO, times(0)).getCartWithProd(anyLong());
        verify(productInCartDAO, times(0)).removeFromCart(any(Product.class), anyLong());
    }

    @Test
    public void guestRemoveProductFromCartTest() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("3").when(request).getParameter("remove_id");
        doReturn(session).when(request).getSession();
        doReturn(1).when(session).getAttribute("access_level");

        doReturn(createSessionCart(10)).when(session).getAttribute("in_cart");

        cartController.processRequest(request, null);

        verify(productDAO, times(9)).getById(anyLong());
        verify(productInCartDAO, times(0)).getCartWithProd(anyLong());
        verify(productInCartDAO, times(0)).removeFromCart(any(Product.class), anyLong());
    }

    @Test
    public void userRemoveProductFromCartTest() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("3").when(request).getParameter("remove_id");
        doReturn(session).when(request).getSession();
        doReturn(2).when(session).getAttribute("access_level");
        //doReturn(anyLong()).when(session).getAttribute("user_id");

        sessionCart = createSessionCart(10);
        doReturn(sessionCart).when(session).getAttribute("in_cart");

        cartController.processRequest(request, null);

        assertEquals(sessionCart.size(), 9); //Product successfully deleted
        verify(productDAO, times(1)).getById(anyLong());
        verify(productInCartDAO, times(1)).getCartWithProd(anyLong());
        verify(productInCartDAO, times(1)).removeFromCart(any(Product.class), anyLong());
    }

    @Test
    public void exceptionWhenRemoveFromUserCart() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        initializeReturnParams(request, session);

        doThrow(new DBException("Test exception!"))
                .when(productInCartDAO).removeFromCart(any(Product.class), anyLong());

        cartController.processRequest(request, null);

        assertEquals(sessionCart.size(), 10);
        verify(productDAO, times(1)).getById(anyLong());
        verify(productInCartDAO, times(1)).getCartWithProd(anyLong());
        verify(productInCartDAO, times(1)).removeFromCart(any(Product.class), anyLong());
    }

    @Test
    public void exceptionProductGetByID() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("27").when(request).getParameter("remove_id");
        doReturn(session).when(request).getSession();
        doReturn(1).when(session).getAttribute("access_level");

        sessionCart = createSessionCart(10);
        doReturn(sessionCart).when(session).getAttribute("in_cart");

        //initializeReturnParams(request, session);

//        doThrow(new DBException("Test exception!"))
//                .when(productInCartDAO).getCartWithProd(anyLong());

        doThrow(new DBException("Test exception!"))
                .when(productDAO).getById(2l);

        doReturn(new Product("test", "test", 1))
                .when(productDAO).getById(1l);

        cartController.processRequest(request, null);

        verify(productDAO, times(10)).getById(anyLong());
    }

    @Test
    public void exWhenGetCartWithProd() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn(session).when(request).getSession();
        doReturn(2).when(session).getAttribute("access_level");
        //doReturn(anyLong()).when(session).getAttribute("user_id");

        sessionCart = createSessionCart(10);
        doReturn(sessionCart).when(session).getAttribute("in_cart");

        doThrow(new DBException("Test")).when(productInCartDAO)
                .getCartWithProd(anyLong());

        cartController.processRequest(request, null);
    }

    private void initializeReturnParams(HttpServletRequest request, HttpSession session) {
        doReturn("3").when(request).getParameter("remove_id");
        doReturn(session).when(request).getSession();
        doReturn(2).when(session).getAttribute("access_level");
        doReturn(27l).when(session).getAttribute("user_id");

        sessionCart = createSessionCart(10);
        doReturn(sessionCart).when(session).getAttribute("in_cart");
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
