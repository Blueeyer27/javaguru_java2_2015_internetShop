package lv.javaguru.java2.servlet.mvc;

import junit.framework.TestCase;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.domain.Product;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void removeWhenCartNullTest() throws Exception {
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
    public void removeProductFromCartTest() throws Exception {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn("27").when(request).getParameter("remove_id");
        doReturn(session).when(request).getSession();
        doReturn(1).when(session).getAttribute("access_level");

        doReturn(createSessionCart(10)).when(session).getAttribute("in_cart");

        cartController.processRequest(request, null);

        verify(productDAO, times(0)).getById(anyLong());
        verify(productInCartDAO, times(0)).getCartWithProd(anyLong());
        verify(productInCartDAO, times(0)).removeFromCart(any(Product.class), anyLong());
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
