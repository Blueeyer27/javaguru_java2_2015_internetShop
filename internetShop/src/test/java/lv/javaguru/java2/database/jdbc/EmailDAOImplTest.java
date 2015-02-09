package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Email;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class EmailDAOImplTest {

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private EmailDAOImpl emailDAO = new EmailDAOImpl();


    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void testCreate() throws DBException {
        Email email = createEmail(1001L, "sunny123@inbox.lv");

        emailDAO.create(email);

        Email emailFromDB = emailDAO.getById(email.getEmailId());
        assertNotNull(emailFromDB);
        assertEquals(email.getEmailId(), emailFromDB.getEmailId());
        assertEquals(email.getClientId(), emailFromDB.getClientId());
        assertEquals(email.getEmailAddr(), emailFromDB.getEmailAddr());
    }


    @Test
    public void testUpdate() throws DBException {

        Email email0 = createEmail(1001L, "sidorov@inbox.lv");
        emailDAO.create(email0);

        Email email= updateEmail(email0, "24444444@gmail.com");
        emailDAO.update(email);

        Email emailFromDB = emailDAO.getById(email.getEmailId());
        assertNotNull(emailFromDB);
        assertEquals(email.getEmailId(), emailFromDB.getEmailId());
        assertEquals(email.getClientId(), emailFromDB.getClientId());
        assertEquals(email.getEmailAddr(), emailFromDB.getEmailAddr());
    }

    @Test
    public void testDelete() throws DBException {

        Email email = createEmail(1001L, "petrova@inbox.lv");
        emailDAO.create(email);
        emailDAO.delete(email.getEmailId());
        Email emailFromDB = emailDAO.getById(email.getEmailId());
        assertNull(emailFromDB);
    }

    @Test
    public void testMultipleEmailsCreation() throws DBException {
        Email email1 = createEmail(1001L, "petrova@inbox.lv");
        Email email2 = createEmail(1002L, "ivanova@inbox.lv");
        Email email3 = createEmail(1003L, "sidorova@inbox.lv");
        emailDAO.create(email1);
        emailDAO.create(email2);
        emailDAO.create(email3);
        List<Email> emails = emailDAO.getAll();
        assertEquals(3, emails.size());
    }


    private Email createEmail(Long clientId, String emailAddr) {
        Email email = new Email();
        email.setClientId(clientId);
        email.setEmailAddr(emailAddr);
        return email;
    }

    private Email updateEmail(Email email, String emailAddr) {
        email.setEmailAddr(emailAddr);
        return email;
    }

}