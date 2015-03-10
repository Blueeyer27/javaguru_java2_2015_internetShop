package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class AboutCompanyControllerTest {

    @Test
    public void testAboutPage() throws Exception {
        AboutCompanyController ac = new AboutCompanyController();

        MVCModel model = ac.safeRequest(null, null);

        assertEquals("/about.jsp", model.getView());
    }
}