package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.*;
import lv.javaguru.java2.database.jdbc.ClientDAOImpl;
import lv.javaguru.java2.database.jdbc.EmailDAOImpl;
import lv.javaguru.java2.database.jdbc.PhoneDAOImpl;
import lv.javaguru.java2.database.jdbc.ProductDAOImpl;
import lv.javaguru.java2.domain.Client;
import lv.javaguru.java2.domain.Email;
import lv.javaguru.java2.domain.Phone;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Anna on 17.02.15.
 */
public class ClientsController implements MVCController {

    public class RezList{
        private List<Client> clients;
        private List<Email> emails;
        private List<Phone> phones;

        public RezList(List<Client> clients, List<Email> emails, List<Phone> phones) {
            this.clients = clients;
            this.emails = emails;
            this.phones = phones;
        }

        public List<Client> getClients() {
            return clients;
        }

        public void setClients(List<Client> clients) {
            this.clients = clients;
        }

        public List<Email> getEmails() {
            return emails;
        }

        public void setEmails(List<Email> emails) {
            this.emails = emails;
        }

        public List<Phone> getPhones() {
            return phones;
        }

        public void setPhones(List<Phone> phones) {
            this.phones = phones;
        }
    }

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {

        ClientDAO clientDAO = new ClientDAOImpl();
        List<Client> clients = null;

        EmailDAO emailDAO = new EmailDAOImpl();
        List<Email> emails = null;

        PhoneDAO phoneDAO = new PhoneDAOImpl();
        List<Phone> phones = null;

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

        Email email_1 = createEmail(client1.getId(), "1111@123.lv");
        Email email_12 = createEmail(client1.getId(), "11aa@123.lv");
        Email email_2 = createEmail(client2.getId(), "222@123.lv");
        Email email_3 = createEmail(client3.getId(), "333@123.lv");
        Email email_31 = createEmail(client3.getId(), "3331@123.lv");
        Email email_32 = createEmail(client3.getId(), "333b@123.lv");


        try {
            emailDAO.create(email_1);
            emailDAO.create(email_12);
            emailDAO.create(email_2);
            emailDAO.create(email_3);
            emailDAO.create(email_31);
            emailDAO.create(email_32);
            emails = emailDAO.getAll();
        } catch (DBException ex) {

        }

        Phone phone_1 = createPhone(client1.getId(), "21111111");
        Phone phone_2 = createPhone(client2.getId(), "21115555");
        Phone phone_21 = createPhone(client2.getId(), "21116666");
        Phone phone_22 = createPhone(client2.getId(), "27771111");
        Phone phone_3 = createPhone(client3.getId(), "21188811");
        Phone phone_31 = createPhone(client3.getId(), "21666111");

        try {
            phoneDAO.create(phone_1);
            phoneDAO.create(phone_2);
            phoneDAO.create(phone_21);
            phoneDAO.create(phone_22);
            phoneDAO.create(phone_3);
            phoneDAO.create(phone_31);
            phones = phoneDAO.getAll();
        } catch (DBException ex) {

        }

        RezList rezult = new RezList(clients, emails, phones);

        MVCModel model = new MVCModel("/client.jsp", rezult);

        for(Client client : clients){
            try {
                clientDAO.delete(client.getId());
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        for(Email email : emails){
            try {
                emailDAO.delete(email.getEmailId());
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

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

    private Email createEmail(Long clientId, String emailAddress){
        Email email = new Email();
        email.setClientId(clientId);
        email.setEmailAddr(emailAddress);
        return email;
    }

    private Phone createPhone(Long clientId, String phoneNumber){
        Phone phone = new Phone();
        phone.setClientId(clientId);
        phone.setPhoneNumber(phoneNumber);
        return phone;
    }

}
