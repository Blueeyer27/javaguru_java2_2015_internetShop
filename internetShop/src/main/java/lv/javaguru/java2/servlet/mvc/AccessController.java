package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.RequestType;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class AccessController implements MVCController {

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();
        String message = null;
        if ((Integer) session.getAttribute("access_level") ==
                AccessLevel.BANNED.getValue()) {
            session.setAttribute("page_name", "Access Denied");

            message = "You have been banned. " +
                    "\nContact site administrator for more information." +
                    "\nexample@gmail.com";
        }
        else {
            //TODO: Check access
            RequestType requestURI = RequestType.getType(request.getServletPath());

            switch(requestURI) {
                case INDEX:
                    session.setAttribute("page_name", "Home page");
                    break;
                case REGISTER:
                    break;
                case LOGIN:
                    break;
                case LOGOUT:
                    break;
                case ABOUT:
                    break;
                case CART:
                    break;
                case PRODUCT:
                    break;
                case NEWS:
                    break;
                case USER:
                    break;
                case ADD_PRODUCT:
                    break;
                default:
                    message = "This page doesn't exist.";
            }
        }

        if (message != null)
            return new MVCModel("/access.jsp", message);

        return safeRequest(request, response);
    }

    abstract MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException;
}
