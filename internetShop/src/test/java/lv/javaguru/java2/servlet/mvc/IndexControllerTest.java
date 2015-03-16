package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.ProductInCartDAO;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;

import static org.mockito.Mockito.doReturn;

/**
 * Created by Anton on 2015.03.15..
 */
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
        indexController.safeRequest(request, null);
    }
}
