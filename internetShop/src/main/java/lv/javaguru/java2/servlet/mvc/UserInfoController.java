package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * Created by Anton on 2015.03.02..
 */

@Component
public class UserInfoController extends AccessController {
    @Autowired
    @Qualifier("ORM_UserDAO")
    UserDAO userDAO;

    private final String UPLOAD_DIRECTORY = "..\\internetShop\\src\\main\\webapp\\images\\users\\";

    @Override
    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();

        String currLogin = (String) session.getAttribute("username");
        if (currLogin == null) return new MVCModel("/user.jsp", "Something wrong with session. " +
                "Please, try to login again.");

        User user = null;

        try {
            user = userDAO.getByLogin(currLogin);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (request.getMethod().equals("POST")) {
            if (ServletFileUpload.isMultipartContent(request)) {
                String fileName = null;

                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);

                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            fileName = new File(item.getName()).getName();
                            item.write(new File(UPLOAD_DIRECTORY + File.separator + fileName));
                        }
                    }

                    System.out.println("File Uploaded Successfully");
                    request.setAttribute("message", "File Uploaded Successfully");
                } catch (Exception ex) {
                    System.out.println("File Upload Failed due to " + ex);
                    request.setAttribute("message", "File Upload Failed due to " + ex);
                }

                if (fileName != null) {
                    try {
                        updateUserPhoto(user, fileName, session);
                    } catch (DBException e) {
                        request.setAttribute("message", "Photo Update Failed due to " + e);
                        e.printStackTrace();
                    }
                }
            } else {
                //TODO: when just info update
            }
        }

        return new MVCModel("/user.jsp", user);
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
}
