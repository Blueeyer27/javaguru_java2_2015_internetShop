package lv.javaguru.java2.servlet.mvc.deprecated_servlets;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.User;

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

    private UserDAOImpl userDAO = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp) throws ServletException, IOException {

		// Set response content type
		resp.setContentType("text/html");

        // Prepare output html
        PrintWriter out = resp.getWriter();

        User testUser = createUser("Anton", "Kamolins", "112233-12345", "male");
        /*User user1 = createUser("Elena", "Ivanova", "220788-23856", "female");
        User user2 = createUser("Ivan", "Petrov", "230189-12303", "male");
        User user3 = createUser("Viktorija", "Smirnova", "150455-34012", "female");*/
        List<User> allUsers = null;

        try {
            userDAO.create(testUser);
            /*userDAO.create(user1);
            userDAO.create(user2);
            userDAO.create(user3);*/
            allUsers = userDAO.getAll();
        } catch (DBException e) {
            out.println("DB error: " + e.getMessage());
            e.printStackTrace();
        }

        if (allUsers == null) out.println("Something wrong with users table in DB.");
        else if (allUsers.size() < 1) out.println("There are no users in your DB.");
        else {
            for (User user : allUsers) {
                out.println("<h1>" + user.getName() + "</h1>");
                out.println("<h3><i>" + user.getSurname() + "</i></h3>\n\n");
            }
        }
	}

    private User createUser(String firstName, String surname, String persCode, String gender) {
        User user = new User();
        user.setName(firstName);
        user.setSurname(surname);
        user.setGender(gender);
        return user;
    }
}
