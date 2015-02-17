package lv.javaguru.java2.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.jdbc.ClientDAOImpl;
import lv.javaguru.java2.domain.Client;


public class ContactsServlet extends HttpServlet {

    private ClientDAOImpl clientDAO = new ClientDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp) throws ServletException, IOException {


		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/contacts.jsp");
		requestDispatcher.forward(req, resp);

	}


}
