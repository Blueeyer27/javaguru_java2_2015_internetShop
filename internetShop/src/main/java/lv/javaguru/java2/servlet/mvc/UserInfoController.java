package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.AccessCheck;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Anton on 2015.03.02..
 */

@Component
public class UserInfoController extends AccessCheck implements MVCController {
    @Autowired
    @Qualifier("ORM_UserDAO")
    UserDAO userDAO;

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        //checkUserAccess(request);
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
            //TODO: update user information
        }

        return new MVCModel("/user.jsp", user);
    }
}
