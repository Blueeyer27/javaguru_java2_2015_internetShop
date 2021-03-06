package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.*;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//@Component
//public class UsersController implements MVCController {

@Controller
public class UsersController {
    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDAO;

    public class RezList{
        private List<User> users;

        public RezList(List<User> users) {
            this.users = users;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

    }

    //@Override
    @RequestMapping(value = "users", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("users");

        List<User> users = null;

        User user1 = createUser("Name1", "Surname1", "111111-11111", "male", "21111111", "111@abc.lv", "login1", "parole1", 1);
        User user2 = createUser("Name2", "Surname2", "222222-22222", "male", "23333333", "122@abc.lv", "login2", "parole2", 2);
        User user3 = createUser("Name3", "Surname3", "333333-33333", "female", "24444444", "133@abc.lv", "login3", "parole3", 3);


        try {
            userDAO.create(user1);
            userDAO.create(user2);
            userDAO.create(user3);
            users = userDAO.getAll();
        } catch (DBException ex) {

        }

        RezList rezult = new RezList(users);

        //MVCModel model = new MVCModel("/users.jsp", rezult);

        model.addObject("model", rezult);
        /*for(User user : users){
            try {
                userDAO.delete(user.getId());
            } catch (DBException e) {
                e.printStackTrace();
            }
        }*/

        return model;
    }

    private User createUser(String name, String surname, String persCode, String gender,
                            String phone, String email, String login, String parole, int level) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setPhone(phone);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(parole);
        user.setAccessLevel(level);
        return user;
    }
}
