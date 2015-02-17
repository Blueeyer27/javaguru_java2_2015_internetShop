package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.ClientDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.jdbc.ClientDAOImpl;
import lv.javaguru.java2.database.jdbc.ProductDAOImpl;
import lv.javaguru.java2.domain.Client;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Anna on 17.02.15.
 */
public class ClientsController implements MVCController {

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        ClientDAO clientDAO = new ClientDAOImpl();
        List<Client> clients = null;

        Client client1 = createClient("Name1", "Surname1", "111111-11111", "1111");
        Client client2 = createClient("Name2", "Surname2", "222222-22222", "2222");
        Client client3 = createClient("Name3", "Surname3", "333333-33333", "333333");

        try {
            clientDAO.create(client1);
            clientDAO.create(client2);
            clientDAO.create(client3);
            clients = clientDAO.getAll();
        } catch (DBException ex) {

        }



        MVCModel model = new MVCModel("/client.jsp", clients);

        /*for(Client client : clients){
            try {
                clientDAO.delete(client.getId());
            } catch (DBException e) {
                e.printStackTrace();
            }
        }*/

        return model;


    }

    private Client createClient(String firstName, String surname, String persCode, String gender) {
        Client client = new Client();
        client.setName(firstName);
        client.setSurname(surname);
        client.setPersCode(persCode);
        client.setGender(gender);
        return client;
    }

}
