package lv.javaguru.java2.servlet;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.jdbc.ClientDAOImpl;
import lv.javaguru.java2.domain.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:viktor.savonin@odnoklassniki.ru">Viktor Savonin</a>
 */
public class HelloWorldServlet extends HttpServlet {

    private ClientDAOImpl clientDAO = new ClientDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp) throws ServletException, IOException {

		// Set response content type
		resp.setContentType("text/html");

        Client client1 = createClient("Elena", "Ivanova", "220788-23856", "female");
        Client client2 = createClient("Ivan", "Petrov", "230189-12303", "male");
        Client client3 = createClient("Viktorija", "Smirnova", "150455-34012", "female");
        List<Client> allClients = null;
        try {
            clientDAO.create(client1);
            clientDAO.create(client2);
            clientDAO.create(client3);
            allClients = clientDAO.getAll();
        } catch (DBException e) {
            e.printStackTrace();
        }

        // Prepare output html
		PrintWriter out = resp.getWriter();
		out.println("<h1>" + allClients.get(1).getName() + "</h1>");
		out.println("<h1>" + allClients.get(2).getName() + "</h1>");
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
