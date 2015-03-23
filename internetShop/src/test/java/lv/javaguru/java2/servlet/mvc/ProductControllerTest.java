package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.CommentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.Comment;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.mock.MockFileItem;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


/**
 * Created by Anton on 2015.03.22..
 */

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock private UserDAO userDAO;
    @Mock private ProductDAO productDAO;
    @Mock private CommentDAO commentDAO;

    @Mock HttpServletRequest request;
    @Mock HttpSession session;

    @Mock private ServletFileUpload mockUpload;
//    private ServletFileUploadCreator mockCreator;
//
//    @Before
//    public void setup() {
//        config = new DefaultMultipartConfig();
//
//        MockitoAnnotations.initMocks(this);
//
//        mockCreator = mock(ServletFileUploadCreator.class);
//        mockUpload = mock(ServletFileUpload.class);
//        when(mockCreator.create(any(FileItemFactory.class))).thenReturn(mockUpload);
//    }

    @InjectMocks
    ProductController productController = new ProductController();

    @Test
    public void loadProductPage() throws Exception {
//        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
//        HttpSession session = Mockito.mock(HttpSession.class);
        when(request.getMethod()).thenReturn("GET");

        productInfoLoad("22");

        ModelAndView model = productController.processRequest(request, null);

        verify(productDAO, times(1)).getById(anyLong());
        verify(commentDAO, times(0)).getAll(anyLong());

        assertTrue(model.getModel().get("model") instanceof Product);
    }

    @Test
    public void commentProduct() throws Exception {
        when(request.getMethod()).thenReturn("POST");

        productInfoLoad("55");

        when(request.getParameter("comment")).thenReturn("test comment");
        doReturn(27l).when(session).getAttribute("user_id");

        ModelAndView model = productController.processRequest(request, null);

        verify(productDAO, times(1)).getById(anyLong());
        verify(userDAO, times(1)).getById(anyLong());
        verify(commentDAO, times(1)).create(any(Comment.class));
    }

    @Test
    public void errorWhenComment() throws Exception {
        when(request.getMethod()).thenReturn("POST");

        productInfoLoad("55");

        when(request.getParameter("comment")).thenReturn("test comment");
        doReturn(27l).when(session).getAttribute("user_id");
        doThrow(new DBException("test")).when(userDAO).getById(anyLong());

        ModelAndView model = productController.processRequest(request, null);

        //verify(productDAO, times(1)).getById(anyLong());
        //verify(userDAO, times(1)).getById(anyLong());
        verify(commentDAO, times(0)).create(any(Comment.class));
    }

    @Test(expected = ClassCastException.class)
    public void wrongUserIdComment() throws Exception {
        when(request.getMethod()).thenReturn("POST");

        productInfoLoad("33");

        when(request.getParameter("comment")).thenReturn("test comment");
        doReturn(22f).when(session).getAttribute("user_id");

        ModelAndView model = productController.processRequest(request, null);

//        verify(userDAO, times(0)).getById(anyLong());
//        verify(commentDAO, times(0)).create(any(Comment.class));
    }

    @Test
    public void uploadWithFieldsOnly() throws Exception {
        doReturn(session).when(request).getSession();
        when(request.getAttribute("id")).thenReturn("22");

        doReturn(new Product("banana", "yellow fruit", 22.55f))
                .when(productDAO).getById(anyLong());

        final List<FileItem> elements = new ArrayList<FileItem>();
        //org.loom.mock.MockFileItem

        elements.add(new MockFileItem("foo", "blah"));
        elements.add(new MockFileItem("bar", "blah blah"));

        when(request.getCharacterEncoding()).thenReturn("utf-8");
        when(request.getContentType()).thenReturn("multipart/form-data");
        when(request.getMethod()).thenReturn("POST");
        when(mockUpload.parseRequest(request)).thenReturn(elements);

        ModelAndView model = productController.processRequest(request, null);
    }

    @Test
    public void uploadWithFieldsAndFiles() throws Exception {
        doReturn(session).when(request).getSession();
        when(request.getAttribute("id")).thenReturn("22");

        doReturn(new Product("banana", "yellow fruit", 22.55f))
                .when(productDAO).getById(anyLong());

        final List<FileItem> elements = new ArrayList<FileItem>();

        //Fields
        elements.add(new MockFileItem("id", "22"));
        elements.add(new MockFileItem("test", "blah blah"));

        //Files
        elements.add(new MockFileItem("thefile0", "test333.txt", "test333".getBytes()));

        when(request.getCharacterEncoding()).thenReturn("utf-8");
        when(request.getContentType()).thenReturn("multipart/form-data");
        when(request.getMethod()).thenReturn("POST");
        when(mockUpload.parseRequest(request)).thenReturn(elements);

        productController.processRequest(request, null);
    }

    private void productInfoLoad(String productID) throws Exception {
        doReturn("text/plain").when(request).getContentType();

        doReturn(session).when(request).getSession();
        doReturn(productID).when(request).getParameter("id");
        when(request.getAttribute("id")).thenReturn(productID);

        doReturn(new Product("banana", "yellow fruit", 22.55f))
                .when(productDAO).getById(anyLong());
    }
}
