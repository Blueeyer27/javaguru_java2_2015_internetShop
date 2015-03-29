package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.RequestType;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class AccessController implements MVCController {

    //@Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();
        String message = null;
        Integer access_level = (Integer) session.getAttribute("access_level");
        String transfer = (String) session.getAttribute("transfer");
        
        if (access_level ==  AccessLevel.BANNED.getValue()) {
            session.setAttribute("page_name", "Access Denied");

            message = "You have been banned. " +
                    "\nContact site administrator for more information." +
                    "\nexample@gmail.com";
        }
        else if (transfer != null
                && !request.getServletPath().equals("/transfer")) {
            System.out.println("Transfer: " + transfer);
            return new MVCModel("/transfer.jsp", "You have products in your cart. " +
                    "Do you want to transfer them on your account?");
        }
        else {
            RequestType requestURI = RequestType.getType(request.getServletPath());

            switch(requestURI) {
                case INDEX:
                    session.setAttribute("page_name", "Home page");
                    if ((request.getParameter("delete") != null)
                            && access_level < AccessLevel.MODERATOR.getValue())
                        message = "Only Moderator or Administrator can delete products.";
                    break;
                case REGISTER:
                    session.setAttribute("page_name", "Registration");
                    if (access_level != AccessLevel.GUEST.getValue())
                        message = "Only guests can access this page.";
                    break;
                case LOGIN:
                    session.setAttribute("page_name", "Login");
                    if (access_level != AccessLevel.GUEST.getValue())
                        message = "Only guests can access this page.";
                    break;
                case LOGOUT:
                    session.setAttribute("page_name", "Logout");
                    if (access_level < AccessLevel.CLIENT.getValue())
                        message = "Only logged in users can access this page.";
                    break;
                case ABOUT:
                    session.setAttribute("page_name", "About Us");
                    break;
                case CART:
                    session.setAttribute("page_name", "My Cart");
                    break;
                case PRODUCT:
                    session.setAttribute("page_name", "About Product");
                    if (ServletFileUpload.isMultipartContent(request))
                        if (access_level < AccessLevel.MODERATOR.getValue())
                            message = "Only Moderator or Administrator " +
                                    "can change product image.";
                    break;
                case NEWS:
                    session.setAttribute("page_name", "News");
                    if (request.getMethod().equals("POST"))
                        //if (access_level < AccessLevel.MODERATOR.getValue())
                        if (access_level < AccessLevel.CLIENT.getValue())
                            message = "Only Moderator or Administrator can add news.";
                    break;
                case USER:
                    session.setAttribute("page_name", "Profile Information");
                    if (access_level < AccessLevel.CLIENT.getValue())
                        message = "Only logged in users can access this page.";
                    break;
                case ADD_PRODUCT:
                    session.setAttribute("page_name", "Add Product");
                    if (access_level < AccessLevel.MODERATOR.getValue())
                        message = "Only Moderator or Administrator can add products.";
                    break;
                case TRANSFER:
                    if (access_level < AccessLevel.CLIENT.getValue())
                        message = "Only registred users can access this page.";
                    else if (transfer == null)
                        message = "You don't have product to transfer.";
                    break;
                default:
                    session.setAttribute("page_name", "Transfer Products");
                    session.setAttribute("page_name", "Access Denied");
                    message = "This page doesn't exist.";
            }
        }

        if (message != null)
            return new MVCModel("/access.jsp", message);

        return safeRequest(request, response);
    }

    abstract MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException;
}
