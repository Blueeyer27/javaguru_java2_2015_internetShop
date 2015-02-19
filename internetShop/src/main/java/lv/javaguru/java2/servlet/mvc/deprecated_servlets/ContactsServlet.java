package lv.javaguru.java2.servlet.mvc.deprecated_servlets;

import lv.javaguru.java2.database.jdbc.UserDAOImpl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ContactsServlet extends HttpServlet {

    private UserDAOImpl userDAO = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp) throws ServletException, IOException {


		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/contacts.jsp");
		requestDispatcher.forward(req, resp);

	}


}
