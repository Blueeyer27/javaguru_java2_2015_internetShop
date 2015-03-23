package lv.javaguru.java2.database.hibernate;

//import junit.framework.TestCase;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.DatabaseCleaner;
import lv.javaguru.java2.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Anton on 2015.03.15..
 */

@WebAppConfiguration
public class UserDAOImplTest extends SpringIntegration {
    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDAO;

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Before
    public void setUp() throws Exception {
        databaseCleaner.cleanDatabase();
    }

//    @After
//    public void tearDown() throws Exception {
//        databaseCleaner.cleanDatabase();
//    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        User user = createUser();

        //assertNull(user.getId());
        userDAO.create(user);
        assertNotNull(user.getId());
    }

    @Test
    @Transactional
    public void testGetByLogin() throws Exception {
        User user = createUser();
        userDAO.create(user);

        User userDB = userDAO.getByLogin(user.getLogin());
        assertNotNull(userDB.getId());
        assertEquals(user.getLogin(), userDB.getLogin());
        assertEquals(user.getPassword(), userDB.getPassword());
        assertEquals(user.getName(), userDB.getName());
        assertEquals(user.getSurname(), userDB.getSurname());
    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<User> newUsers = generateUsers(10);

        for (User user : newUsers) {
            userDAO.create(user);
        }
        List<User> dbUsers = userDAO.getAll();

        assertEquals(dbUsers.size(), newUsers.size());
    }

    private List<User> generateUsers(int userCount) {
        List<User> newUsers = new ArrayList<User>();
        for (int i = 1; i <= userCount; i++) {
            User user = new User("name" + i, "surname" + i, "male",
                    "+89612059405", "email" + i + "@gmail.com",
                    "login" + i, "password" + i, randomInt(0,4));
            newUsers.add(user);
        }

        return  newUsers;
    }

    private int randomInt(int min, int max) {
        return min + (int)(Math.random() * (max + 1));
    }

    private User createUser() {
        return new User("Iosif", "Petrov", "male",
                "+89612059407", "iosif,petrov@gmail.com",
                "iosi4ka", "parolik", 2);
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        User user = createUser();
        userDAO.create(user);

        User userDB = userDAO.getByLogin(user.getLogin());
        assertNotNull(userDB);

        userDAO.delete(userDB.getId());
        userDB = userDAO.getByLogin(user.getLogin());
        assertNull(userDB);
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        User user = createUser();
        userDAO.create(user);

        assertNotNull(userDAO.getById(user.getId()));

        String newPassword = "NewPassword";
        user.setPassword(newPassword);
        userDAO.update(user);
        assertNotNull(userDAO.getById(user.getId()));
        assertEquals(newPassword,
                userDAO.getById(user.getId()).getPassword());
    }
}
