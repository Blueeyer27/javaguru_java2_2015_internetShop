package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

public class LoginControllerTest {

    @Test
    public void testLoggedInUserWantsToLogOut() throws Exception {
        LoginController lc = new LoginController();

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);

        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(session.getAttribute("access_level")).thenReturn(2);
        //
        Mockito.when(req.getServletPath()).thenReturn("/logout");
        Mockito.when(req.getSession(true)).thenReturn(session);

        MVCModel model = lc.processRequest(req, null);
        assertEquals("/logout.jsp", model.getView());
    }


    @Test
    public void testLoggedInUserWantsToOpenLoginPage() throws Exception {
        LoginController lc = new LoginController();
        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(req.getServletPath()).thenReturn("/login");
        Mockito.when(session.getAttribute("access_level")).thenReturn(2);
        Mockito.when(req.getSession(true)).thenReturn(session);

        MVCModel model = lc.processRequest(req, null);

        assertEquals("/access.jsp", model.getView());

    }
}