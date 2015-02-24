package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AboutCompanyController implements MVCController {
    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

        return new MVCModel("/about.jsp");
    }
}
