package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.CommentDAO;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Anton on 2015.03.22..
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private ProductDAO productDAO;

    @Mock
    private CommentDAO commentDAO;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @InjectMocks
    ProductController productController = new ProductController();

    @Test
    public void loadProductPage() throws Exception {
        doReturn(session).when(request).getSession();
        doReturn("22")
                .when(request).getParameter("id");

        doReturn(any(Product.class)).when(productDAO).getById(anyLong());

        ModelAndView model = productController.processRequest(request, null);

        verify(productDAO, times(1)).getById(anyLong());
        verify(commentDAO, times(1)).getAll(anyLong());
        //assertTrue(model.ob);
    }
}
