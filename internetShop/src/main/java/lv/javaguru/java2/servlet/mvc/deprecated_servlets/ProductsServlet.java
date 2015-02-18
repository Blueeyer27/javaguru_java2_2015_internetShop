package lv.javaguru.java2.servlet.mvc.deprecated_servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.jdbc.ClientDAOImpl;
import lv.javaguru.java2.database.jdbc.ProductDAOImpl;
import lv.javaguru.java2.domain.Product;


public class ProductsServlet extends HttpServlet {

    private ProductDAOImpl productDAO = new ProductDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req,
	                     HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        Product product = new Product("Banana", "This is fruit.", 0.59f);
        try {
            productDAO.create(product);
        } catch (DBException e) {
            out.println("<h1>DB error: " + e.getMessage() + "</h1>");
        }
        out.println("<h1>DONE!</h1>");
        //PrintWriter out = resp.getWriter();

		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/products.jsp");
		requestDispatcher.forward(req, resp);

	}


}
