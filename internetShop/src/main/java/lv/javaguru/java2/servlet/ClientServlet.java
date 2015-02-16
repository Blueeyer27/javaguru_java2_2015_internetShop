package lv.javaguru.java2.servlet;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.jdbc.ClientDAOImpl;
import lv.javaguru.java2.domain.Client;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna on 11.02.15.
 */
public class ClientServlet extends HttpServlet {

    private ClientDAOImpl clientDAO = new ClientDAOImpl();

    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        // Set response content type
        resp.setContentType("text/html");

        // Prepare output html
        PrintWriter out = resp.getWriter();

        Client client1 = createClient("Name1", "Surname1", "111111-11111", "1111");
        Client client2 = createClient("Name2", "Surname2", "222222-22222", "2222");
        Client client3 = createClient("Name3", "Surname3", "333333-33333", "333333");

        List<Client> allClients = null;

        try {
            clientDAO.create(client1);
            clientDAO.create(client2);
            clientDAO.create(client3);
            allClients = clientDAO.getAll();
        } catch (DBException e) {
            out.println("DB error: " + e.getMessage());
            e.printStackTrace();
        }

        if (allClients == null) out.println("Something wrong with clients table in DB.");
        else if (allClients.size() < 1) out.println("There are no clients in your DB.");
        else {
            req.setAttribute("quantity", allClients.size());

            List dataList = new ArrayList();
            for(Client client : allClients){
                dataList.add(client.getId());
                dataList.add(client.getName());
                dataList.add(client.getSurname());
                dataList.add(client.getPersCode());
                dataList.add(client.getGender());
            }
            req.setAttribute("data", dataList);

            for(Client client : allClients){
                try {
                    clientDAO.delete(client.getId());
                } catch (DBException e) {
                    e.printStackTrace();
                }
            }
        }

        // Prepare output html
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/client.jsp");
        requestDispatcher.forward(req, resp);
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
