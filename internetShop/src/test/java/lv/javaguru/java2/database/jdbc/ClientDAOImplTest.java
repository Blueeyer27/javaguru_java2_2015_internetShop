package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Client;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class ClientDAOImplTest {

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private ClientDAOImpl clientDAO = new ClientDAOImpl();


    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void testCreate() throws DBException {
        Client client = createClient("Ivan", "Petrov", "29876645", "petrov@inbox.lv");

        clientDAO.create(client);

        Client clientFromDB = clientDAO.getById(client.getId());
        assertNotNull(clientFromDB);
        assertEquals(client.getId(), clientFromDB.getId());
        assertEquals(client.getName(), clientFromDB.getName());
        assertEquals(client.getSurname(), clientFromDB.getSurname());
        assertEquals(client.getPhone(), clientFromDB.getPhone());
        assertEquals(client.geteMail(), clientFromDB.geteMail());
    }


    @Test
    public void testUpdate() throws DBException {

        Client client0 = createClient("Elena", "Ivanova", "29776523", "ivanova@gmail.com");
        clientDAO.create(client0);

        Client client = updateClient(client0, "24444444");
        clientDAO.update(client);

        Client clientFromDB = clientDAO.getById(client.getId());
        assertNotNull(clientFromDB);
        assertEquals(client.getId(), clientFromDB.getId());
        assertEquals(client.getName(), clientFromDB.getName());
        assertEquals(client.getSurname(), clientFromDB.getSurname());
        assertEquals(client.getPhone(), clientFromDB.getPhone());
        assertEquals(client.geteMail(), clientFromDB.geteMail());
    }

    @Test
    public void testDelete() throws DBException {

        Client client = createClient("Viktorija", "Smirnova", "29081251", "smirnova@gmail.com");
        clientDAO.create(client);
        clientDAO.delete(client.getId());
        Client clientFromDB = clientDAO.getById(client.getId());
        assertNull(clientFromDB);
    }

    @Test
    public void testMultipleClientsCreation() throws DBException {
        Client client1 = createClient("Elena", "Ivanova", "29776523", "ivanova@gmail.com");
        Client client2 = createClient("Ivan", "Petrov", "29876645", "petrov@inbox.lv");
        Client client3 = createClient("Viktorija", "Smirnova", "29081251", "smirnova@gmail.com");
        clientDAO.create(client1);
        clientDAO.create(client2);
        clientDAO.create(client3);
        List<Client> clients = clientDAO.getAll();
        assertEquals(3, clients.size());
    }



    private Client createClient(String firstName, String surname, String phone, String eMail) {
        Client client = new Client();
        client.setName(firstName);
        client.setSurname(surname);
        client.setPhone(phone);
        client.seteMail(eMail);
        return client;
    }

    private Client updateClient(Client client, String phone) {
        client.setPhone(phone);
        return client;
    }

}