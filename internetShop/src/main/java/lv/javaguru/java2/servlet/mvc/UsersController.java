package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.*;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.User;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Anna on 17.02.15.
 */
public class UsersController implements MVCController {

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

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

        UserDAO userDAO = new UserDAOImpl();
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

        MVCModel model = new MVCModel("/users.jsp", rezult);

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
