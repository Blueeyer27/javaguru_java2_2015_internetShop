package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Phone;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class PhoneDAOImplTest {

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private PhoneDAOImpl phoneDAO = new PhoneDAOImpl();


    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void testCreate() throws DBException {
        Phone phone = createPhone(1001L, "22870956");

        phoneDAO.create(phone);

        Phone phoneFromDB = phoneDAO.getById(phone.getPhoneId());
        assertNotNull(phoneFromDB);
        assertEquals(phone.getPhoneId(), phoneFromDB.getPhoneId());
        assertEquals(phone.getClientId(), phoneFromDB.getClientId());
        assertEquals(phone.getPhoneNumber(), phoneFromDB.getPhoneNumber());
    }


    @Test
    public void testUpdate() throws DBException {

        Phone phone0 = createPhone(1001L, "23333333");
        phoneDAO.create(phone0);

        Phone phone = updatePhone(phone0, "24444444");
        phoneDAO.update(phone);

        Phone phoneFromDB = phoneDAO.getById(phone.getPhoneId());
        assertNotNull(phoneFromDB);
        assertEquals(phone.getPhoneId(), phoneFromDB.getPhoneId());
        assertEquals(phone.getClientId(), phoneFromDB.getClientId());
        assertEquals(phone.getPhoneNumber(), phoneFromDB.getPhoneNumber());
    }

    @Test
    public void testDelete() throws DBException {

        Phone phone = createPhone(1001L, "25555555");
        phoneDAO.create(phone);
        phoneDAO.delete(phone.getPhoneId());
        Phone phoneFromDB = phoneDAO.getById(phone.getPhoneId());
        assertNull(phoneFromDB);
    }

    @Test
    public void testMultiplePhonesCreation() throws DBException {
        Phone phone1 = createPhone(1001L, "25555555");
        Phone phone2 = createPhone(1001L, "27777777");
        Phone phone3 = createPhone(1001L, "28888888");
        phoneDAO.create(phone1);
        phoneDAO.create(phone2);
        phoneDAO.create(phone3);
        List<Phone> phones = phoneDAO.getAll();
        assertEquals(3, phones.size());
    }


    private Phone createPhone(Long clientId, String phoneNumber) {
        Phone phone = new Phone();
        phone.setClientId(clientId);
        phone.setPhoneNumber(phoneNumber);
        return phone;
    }

    private Phone updatePhone(Phone phone, String phoneNumber) {
        phone.setPhoneNumber(phoneNumber);
        return phone;
    }

}