package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        controllerMapping.put("/index", new IndexController());
        controllerMapping.put("/users", new UsersController());
        controllerMapping.put("/register", new RegisterController());
        controllerMapping.put("/login", new LoginController());
        controllerMapping.put("/logout", new LoginController());
        controllerMapping.put("/about", new AboutCompanyController());
        controllerMapping.put("/cart", new CartController());
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        String contextURI = req.getServletPath();
        System.out.println(contextURI);
        String path = ((HttpServletRequest) request).getRequestURI();

        HttpSession session = req.getSession(true);

        if (session.isNew()) {
            session.setAttribute("in_cart", new HashMap<Long, Integer>()); // empty cart (<prodID, count>)
            session.setAttribute("access_level", AccessLevel.GUEST.getValue());
        }

        if (contextURI.equals("/index.jsp"))
            contextURI = "/index";

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