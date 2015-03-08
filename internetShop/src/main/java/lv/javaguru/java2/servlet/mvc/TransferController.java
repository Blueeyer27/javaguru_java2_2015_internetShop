package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by Anton on 2015.03.08..
 */

@Component
public class TransferController extends AccessController {
    @Override
    MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        if(request.getParameter("answer") != null) {
            String answer = request.getParameter("answer");

            HttpSession session = request.getSession();

            if (answer.equals("yes")) {
                System.out.println("Answer is YES");
            } else {
                System.out.println("Answer is NO");
                session.removeAttribute("transfer");
                session.removeAttribute("in_cart");
                session.setAttribute("in_cart", new HashMap<Long, Integer>());
            }
        }
        return new MVCModel("/transfer.jsp", null);
    }
}
