package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class UserDAOImplTest {

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private UserDAOImpl userDAO = new UserDAOImpl();


    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void testCreate() throws DBException {
        User user = createUser("Name1", "Surname1", "111111-11111", "male", "21111111", "111@abc.lv", "login1", "parole1", 1);

        userDAO.create(user);

        User userFromDB = userDAO.getById(user.getId());
        assertNotNull(userFromDB);
        assertEquals(user.getId(), userFromDB.getId());
        assertEquals(user.getName(), userFromDB.getName());
        assertEquals(user.getSurname(), userFromDB.getSurname());
        assertEquals(user.getGender(), userFromDB.getGender());
        assertEquals(user.getPhone(), userFromDB.getPhone());
        assertEquals(user.getEmail(), userFromDB.getEmail());
        assertEquals(user.getLogin(), userFromDB.getLogin());
    }

   /* @Test
    public void testMultipleUserCreation() throws DBException {
        User user1 = createUser("F1", "L1");
        User user2 = createUser("F2", "L2");
        userDAO.create(user1);
        userDAO.create(user2);
        List<User> users = userDAO.getAll();
        assertEquals(2, users.size());
    }*/

    /*@Test
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
    }*/

    /*@Test
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
    }*/

    private User createUser(String name, String surname, String persCode, String gender,
                            String phone, String email, String login, String parole, int level) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setGender(gender);
        user.setPhone(phone);
        user.setEmail(email);
        user.setLogin(login);
        return user;
    }

}