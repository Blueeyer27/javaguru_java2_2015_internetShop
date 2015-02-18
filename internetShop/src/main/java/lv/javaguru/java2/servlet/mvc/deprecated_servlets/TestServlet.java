package lv.javaguru.java2.servlet.mvc.deprecated_servlets;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.DatabaseCleaner;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp) throws ServletException, IOException {

		// Set response content type
		resp.setContentType("text/html");

        // Prepare output html
        PrintWriter out = resp.getWriter();

        //Cleanup database
//        DatabaseCleaner databaseCleaner = new DatabaseCleaner();
//        try {
//            databaseCleaner.cleanDatabase();
//        } catch (DBException e) {
//            e.printStackTrace();
//        }

        //Prepare user DAO
        UserDAOImpl UserDAO = new UserDAOImpl();

        //Create users
        User user = new User();
        user.setFirstName("Firstname1");
        user.setLastName("Lastname1");

        try {
            UserDAO.create(user);
        } catch (DBException e) {
            e.printStackTrace();
        }

        User user2 = new User();
        user2.setFirstName("Firstname2");
        user2.setLastName("Lastname2");

        try {
            UserDAO.create(user2);
        } catch (DBException e) {
            e.printStackTrace();
        }

        //Get list
        try {
            List<User> users = UserDAO.getAll();

            for (User userData: users) {
                out.println("<h1>" + userData.getFirstName() + "" + userData.getLastName() + "</h1>");
            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        out.println("<h1>" + "Hello WWW world from Java3!" + "</h1>");
		out.println("<h1>" + "Hello WWW world from Java!" + "</h1>");
	}

}
