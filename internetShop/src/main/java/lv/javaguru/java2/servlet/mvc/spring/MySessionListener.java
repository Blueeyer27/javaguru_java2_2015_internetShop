package lv.javaguru.java2.servlet.mvc.spring;

/**
 * Created by Anton on 2015.03.17..
 */
import lv.javaguru.java2.AccessLevel;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.HashMap;

@WebListener
public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();

        // Adding empty cart to session <productID, productCount>
        session.setAttribute("in_cart", new HashMap<Long, Integer>());
        session.setAttribute("access_level", AccessLevel.GUEST.getValue());
        session.setAttribute("liked", new ArrayList<Long>());

        System.out.println("Session Created:: ID=" + sessionEvent.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        System.out.println("Session Destroyed:: ID=" + sessionEvent.getSession().getId());
    }

}
