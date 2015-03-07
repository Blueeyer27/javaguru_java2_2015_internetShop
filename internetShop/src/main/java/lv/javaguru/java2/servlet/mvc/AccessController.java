package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
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
                AccessLevel.BANNED.getValue())
            message = "User is banned";
        else {
            //TODO: Check access
            String reqURI = request.getRequestURI();
            if (reqURI.equals("/index")) {

            } else if (reqURI.equals("/login")) {

            } else if (reqURI.equals("/logout")) {

            } else if (reqURI.equals("/register")) {

            } else if (reqURI.equals("/about")) {

            } else message = "This page don't exist.";
        }

        //This method (and class) is in progress so at this moment it throws exception
        throw new NotImplementedException();
    }

    abstract MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException;
}
