package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alex on 17/02/15.
 */
public class HelloWorldController implements MVCController {

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        String message = "Hello from MVC!";
        MVCModel model = new MVCModel("/helloWorld.jsp", message);
        return model;
    }
}
