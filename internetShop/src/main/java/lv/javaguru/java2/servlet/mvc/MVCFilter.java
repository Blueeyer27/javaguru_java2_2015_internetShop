package lv.javaguru.java2.servlet.mvc;

import lv.javaguru.java2.AccessLevel;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import lv.javaguru.java2.servlet.mvc.spring.SpringAppConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alex on 17/02/15.
 */
public class MVCFilter implements Filter {

    private static Logger logger = Logger.getLogger(MVCFilter.class.getName());

    private ApplicationContext springContext;

    private Map<String, MVCController> controllerMapping;

    @Override
    public void init (FilterConfig filterConfig) throws ServletException {

        try {
            springContext =
                    new AnnotationConfigApplicationContext(SpringAppConfig.class);
        } catch (BeansException e) {
            logger.log(Level.INFO, "Spring context failed to start", e);
        }

        controllerMapping = new HashMap<String, MVCController>();
        controllerMapping.put("/index", getBean(IndexController.class));
        controllerMapping.put("/users", getBean(UsersController.class));
        controllerMapping.put("/register", getBean(RegisterController.class));
        controllerMapping.put("/login", getBean(LoginController.class));
        controllerMapping.put("/logout", getBean(LoginController.class));
        controllerMapping.put("/about", getBean(AboutCompanyController.class));
        controllerMapping.put("/cart", getBean(CartController.class));
        controllerMapping.put("/product", getBean(ProductController.class));
        controllerMapping.put("/news", getBean(NewItemController.class));
        controllerMapping.put("/user", getBean(UserInfoController.class));

        /*
        TODO:
        controllerMapping.put("/purchase", getBean(PurchaseController.class));
        controllerMapping.put("/admin", getBean(AdminController.class));
         */
    }

    private MVCController getBean(Class clazz){
        return (MVCController) springContext.getBean(clazz);
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
            session.setAttribute("liked", new ArrayList<String>());
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