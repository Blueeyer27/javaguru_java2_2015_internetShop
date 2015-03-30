package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.PasswordHash;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.AccessCheck.AccessChecker;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Anton on 2015.03.02..
 */

//@Component
//public class UserInfoController extends AccessController {

@Controller
public class UserInfoController {
    @Autowired
    @Qualifier("ORM_UserDAO")
    UserDAO userDAO;

    //For linux:
    //private final String UPLOAD_DIRECTORY = "../internetShop/src/main/webapp/images/users";

    //For windows:
    private final String UPLOAD_DIRECTORY = "..\\internetShop\\src\\main\\webapp\\images\\users\\";


//    @Override
//    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

    @RequestMapping(value = "user", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = AccessChecker.check(request);
        if (model != null) return model;

        model = new ModelAndView("user");
        HttpSession session = request.getSession();
        //session.setAttribute("page_name", "User Information");

        String currLogin = (String) session.getAttribute("username");
        if (currLogin == null) {
//            return new MVCModel("/user.jsp", "Something wrong with session. " +
//                    "Please, try to login again.");
            return model.addObject("model", "Something wrong with session. " +
                    "Please, try to login again.");
        }

        User user = null;

        try {
            user = userDAO.getByLogin(currLogin);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (request.getMethod().equals("POST")) {
            if (ServletFileUpload.isMultipartContent(request)) {
                String fileName = null;
                boolean isUploaded = false;

                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);



                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            if(isUploaded) continue;

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyhhmmss");
                            fileName = new File(simpleDateFormat.format(new Date())
                                    + item.getName()).getName();

                            //fileName = new File(item.getName()).getName();
                            item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
                            isUploaded = true;
                        }
                    }

                    System.out.println("File Uploaded Successfully");
                    request.setAttribute("message", "File Uploaded Successfully");
                } catch (Exception ex) {
                    System.out.println("File Upload Failed due to " + ex);
                    request.setAttribute("message", "File Upload Failed due to " + ex);
                }

                if (isUploaded && fileName != null) {
                    try {
                        updateUserPhoto(user, fileName, session);
                    } catch (DBException e) {
                        request.setAttribute("message", "Photo Update Failed due to " + e);
                        e.printStackTrace();
                    }
                }
            } else {
                // if Key "Update" pressed
                if (request.getParameter("update") != null) {
                    if (!checkFields(request)) {
                        request.setAttribute("message", "Fields can't be empty...");
                        //return new MVCModel("/user.jsp", user);
                        return model.addObject("model", user);
                    } else {
                        updateUserInfo(user, request);

                        boolean updated = true;
                        try {
                            userDAO.update(user);
                        } catch (DBException e) {
                            updated = false;
                            e.printStackTrace();
                        }

                        if (updated) {
                            session.removeAttribute("name");
                            session.removeAttribute("surname");

                            session.setAttribute("name", user.getName());
                            session.setAttribute("surname", user.getSurname());
                        }
                    }
                } else if (request.getParameter("change") != null) {
                    String password = request.getParameter("password");
                    boolean verify = false;
                    try {
                        verify = PasswordHash.validatePassword(password, user.getPassword());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }

                    if (verify) {
                        String newPassword = request.getParameter("new_password1");
                        if (newPassword.equals(request.getParameter("new_password2"))
                                && newPassword.length() >= 6) {
                            try {
                                user.setPassword(PasswordHash.createHash(newPassword));
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (InvalidKeySpecException e) {
                                e.printStackTrace();
                            }

                            try {
                                userDAO.update(user);
                            } catch (DBException e) {
                                request.setAttribute("message", "Something wrong with DB. Try again.");
                                e.printStackTrace();
                            }
                        } else
                            request.setAttribute("message", "New passwords aren't same. " +
                                            "Or password length is less than 6 characters.");
                    } else {
                        request.setAttribute("message", "Incorrect password!");
                    }
                }
            }
        }

        //return new MVCModel("/user.jsp", user);
        return model.addObject("model", user);
    }

    private void updateUserInfo(User user, HttpServletRequest request) {
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setGender(request.getParameter("gender"));
        user.setPhone(request.getParameter("phone"));
        user.setEmail(request.getParameter("email"));
    }

    private void updateUserPhoto(User user, String fileName, HttpSession session)
            throws DBException {
        if (user.getPhotoURL() != null) {
            //TODO: delete previous image
        }

        user.setPhotoURL("/images/users/" + fileName);
        userDAO.update(user);

        session.removeAttribute("photo");
        session.setAttribute("photo", "/images/users/" + fileName);
    }

    private boolean checkFields(HttpServletRequest request) {
        return !(request.getParameter("name").isEmpty()
                || request.getParameter("surname").isEmpty()
                || request.getParameter("gender").isEmpty()
                || request.getParameter("phone").isEmpty()
                || request.getParameter("email").isEmpty());
    }
}
