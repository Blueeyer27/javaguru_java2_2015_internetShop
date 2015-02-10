package lv.javaguru.java2.database.jdbc;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.User;


public class UserDAOImplTest {

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private UserDAOImpl userDAO = new UserDAOImpl();


    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void testCreate() throws DBException {
        User user = createUser("F", "L");

        userDAO.create(user);

        User userFromDB = userDAO.getById(user.getUserId());
        assertNotNull(userFromDB);
        assertEquals(user.getUserId(), userFromDB.getUserId());
        assertEquals(user.getFirstName(), userFromDB.getFirstName());
        assertEquals(user.getLastName(), userFromDB.getLastName());
    }

    @Test
    public void testMultipleUserCreation() throws DBException {
        User user1 = createUser("F1", "L1");
        User user2 = createUser("F2", "L2");
        userDAO.create(user1);
        userDAO.create(user2);
        List<User> users = userDAO.getAll();
        assertEquals(2, users.size());
    }

    @Test
    public void testUpdate() throws DBException {
        User user = createUser("F3", "L3");
        userDAO.create(user);
        User userFromDBBefore = userDAO.getById(user.getUserId());

        userFromDBBefore.setFirstName("updated");
        userFromDBBefore.setLastName("updated");
        userDAO.update(userFromDBBefore);

        User userFromDBAfter = userDAO.getById(userFromDBBefore.getUserId());

        assertNotNull(userFromDBBefore);
        assertNotNull(userFromDBAfter);
        assertEquals(userFromDBBefore.getUserId(), userFromDBAfter.getUserId());
        assertEquals(userFromDBBefore.getFirstName(), userFromDBAfter.getFirstName());
        assertEquals(userFromDBBefore.getLastName(), userFromDBAfter.getLastName());
    }

    @Test
    public void testDelete() throws DBException {
        User user = createUser("F4", "F4");
        userDAO.create(user);
        User userFromDB = userDAO.getById(user.getUserId());

        //Test if user exists
        assertNotNull(userFromDB);
        assertEquals(user.getUserId(), userFromDB.getUserId());
        assertEquals(user.getFirstName(), userFromDB.getFirstName());
        assertEquals(user.getLastName(), userFromDB.getLastName());

        userDAO.delete(user.getUserId());
        userFromDB = userDAO.getById(user.getUserId());
        assertNull(userFromDB);
    }

    private User createUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

}