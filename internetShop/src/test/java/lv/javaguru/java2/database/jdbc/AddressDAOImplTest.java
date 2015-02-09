package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Address;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class AddressDAOImplTest {

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private AddressDAOImpl addressDAO = new AddressDAOImpl();


    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void testCreate() throws DBException {
        Address address = createAddress(1001L, "Latvia", "Riga", "Brivibas", 202, 12, "LV10-22");

        addressDAO.create(address);

        Address addressFromDB = addressDAO.getById(address.getAddressId());
        assertNotNull(addressFromDB);
        assertEquals(address.getAddressId(), addressFromDB.getAddressId());
        assertEquals(address.getClientId(), addressFromDB.getClientId());
        assertEquals(address.getCountry(), addressFromDB.getCountry());
        assertEquals(address.getCity(), addressFromDB.getCity());
        assertEquals(address.getStreet(), addressFromDB.getStreet());
        assertEquals(address.getHouseNo(), addressFromDB.getHouseNo());
        assertEquals(address.getFlatNo(), addressFromDB.getFlatNo());
        assertEquals(address.getPostcode(), addressFromDB.getPostcode());
    }


    @Test
    public void testUpdate() throws DBException {

        Address address0 = createAddress(1001L, "Latvia", "Riga", "Gertrudes", 50, 202, "LV10-32");
        addressDAO.create(address0);

        Address address = updateAddress(address0, "Jurmala");
        addressDAO.update(address);

        Address addressFromDB = addressDAO.getById(address.getAddressId());
        assertNotNull(addressFromDB);
        assertEquals(address.getAddressId(), addressFromDB.getAddressId());
        assertEquals(address.getClientId(), addressFromDB.getClientId());
        assertEquals(address.getCountry(), addressFromDB.getCountry());
        assertEquals(address.getCity(), addressFromDB.getCity());
        assertEquals(address.getStreet(), addressFromDB.getStreet());
        assertEquals(address.getHouseNo(), addressFromDB.getHouseNo());
        assertEquals(address.getFlatNo(), addressFromDB.getFlatNo());
        assertEquals(address.getPostcode(), addressFromDB.getPostcode());
    }

    @Test
    public void testDelete() throws DBException {

        Address address = createAddress(1001L, "Latvia", "Riga", "Gertrudes", 50, 202, "LV10-32");
        addressDAO.create(address);
        addressDAO.delete(address.getAddressId());
        Address addressFromDB = addressDAO.getById(address.getAddressId());
        assertNull(addressFromDB);
    }

    @Test
    public void testMultipleAddressesCreation() throws DBException {
        Address address1 = createAddress(1001L, "Latvia", "Riga", "Gertrudes", 50, 202, "LV10-32");
        Address address2 = createAddress(1002L, "Latvia", "Jurmala", "Jomas", 11, 4, "LV10-32");
        Address address3 = createAddress(1003L, "Latvia", "Jelgava", "Rigas", 100, 2, "LV10-32");
        addressDAO.create(address1);
        addressDAO.create(address2);
        addressDAO.create(address3);
        List<Address> addresses = addressDAO.getAll();
        assertEquals(3, addresses.size());
    }


    private Address createAddress(Long clientId, String country, String city, String street, int house, int flat, String postcode) {
        Address address = new Address();
        address.setClientId(clientId);
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setHouseNo(house);
        address.setFlatNo(flat);
        address.setPostcode(postcode);

        return address;
    }

    private Address updateAddress(Address address, String city) {
        address.setCity(city);
        return address;
    }

}