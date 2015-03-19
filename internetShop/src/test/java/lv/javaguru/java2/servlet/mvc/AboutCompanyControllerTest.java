package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class AboutCompanyControllerTest {

    @Test
    public void testAboutPage() throws Exception {
        AboutCompanyController ac = new AboutCompanyController();

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        doReturn(session).when(request).getSession();
        //MVCModel model = ac.safeRequest(null, null);

        ModelAndView model = ac.processRequest(request, null);
        //assertEquals("/about.jsp", model.getView());
        assertEquals("about", model.getViewName());
    }
}