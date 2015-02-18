package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import com.sun.xml.internal.bind.v2.TODO;
import lv.javaguru.java2.domain.Client;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Anton on 2015.02.18..
 */
public class RegisterController implements MVCController {
    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //TODO: Connect to DB and check all data

        HttpSession session = request.getSession(true);
        session.setAttribute("username", username);

        //TODO: registration logic

        return null;
    }
}
