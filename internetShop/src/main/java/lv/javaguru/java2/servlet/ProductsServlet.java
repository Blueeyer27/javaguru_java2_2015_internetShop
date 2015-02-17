package lv.javaguru.java2.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lv.javaguru.java2.database.jdbc.ClientDAOImpl;


public class ProductsServlet extends HttpServlet {

    private ClientDAOImpl clientDAO = new ClientDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp) throws ServletException, IOException {


		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
		requestDispatcher.forward(req, resp);

	}


}
