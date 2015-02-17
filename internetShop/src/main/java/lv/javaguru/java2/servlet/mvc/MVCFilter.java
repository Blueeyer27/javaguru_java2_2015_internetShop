package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alex on 17/02/15.
 */
public class MVCFilter implements Filter {

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String contextURI = req.getServletPath();
        String path = ((HttpServletRequest) request).getRequestURI();

        if (("/hello").equals(contextURI)) {
            HelloWorldController controller = new HelloWorldController();
            MVCModel model = controller.processRequest(req, resp);

            req.setAttribute("model", model.getData());
            ServletContext context = req.getServletContext();
            System.out.println("View: " + model.getView());

            RequestDispatcher requestDispatcher = context.getRequestDispatcher(model.getView());
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}