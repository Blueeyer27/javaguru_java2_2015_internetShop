package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.NewItemDAO;
import lv.javaguru.java2.domain.NewItem;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NewsControllerTest {
    @Test
    public void testGettingNewsFromDB() throws Exception {
        NewsController nc = new NewsController();
        HttpSession session = Mockito.mock(HttpSession.class);

        HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
        Mockito.when(req.getMethod()).thenReturn("POST");
        Mockito.when(req.getSession()).thenReturn(session);

        NewItemDAO nid = Mockito.mock(NewItemDAO.class);
        nc.setNewItemDAO(nid);
        List<NewItem> niList = new ArrayList<NewItem>();
        niList.add(null);
        niList.add(null);
        niList.add(null);
        niList.add(null);
        niList.add(null);

        Mockito.doThrow(DBException.class).when(nid).create((NewItem) Mockito.anyObject());

        Mockito.when(nid.getAll()).thenReturn(niList);

        MVCModel model = nc.safeRequest(req, null);
        assertEquals("/news.jsp", model.getView());
    }
}