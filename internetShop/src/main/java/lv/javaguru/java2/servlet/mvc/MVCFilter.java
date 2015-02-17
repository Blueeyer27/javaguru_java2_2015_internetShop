package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 17/02/15.
 */
public class MVCFilter implements Filter {

    private Map<String, MVCController> controllerMapping;

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {
        controllerMapping = new HashMap<String, MVCController>();
        controllerMapping.put("/hello", new HelloWorldController());
        controllerMapping.put("/products", new ProductsController());
        controllerMapping.put("/clients", new ClientsController());
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String contextURI = req.getServletPath();
        String path = ((HttpServletRequest) request).getRequestURI();

        if (controllerMapping.keySet().contains(contextURI)){
            MVCController controller = controllerMapping.get(contextURI);
            MVCModel model = controller.processRequest(req, resp);

            req.setAttribute("model", model.getData());
            ServletContext context = req.getServletContext();
            System.out.println("View: " + model.getView());

            RequestDispatcher requestDispatcher = context.getRequestDispatcher(model.getView());
            requestDispatcher.forward(req, resp);
        }
        else filterChain.doFilter(request,response);


    }

    @Override
    public void destroy() {

    }
}