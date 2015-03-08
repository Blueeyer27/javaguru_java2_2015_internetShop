package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.NewItemDAO;
import lv.javaguru.java2.domain.NewItem;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by Anna on 27.02.15.
 */

@Component
public class NewsController extends AccessController {

    @Autowired
    private NewItemDAO newItemDAO;
    Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();

        List<String> likedItems = (ArrayList<String>) session.getAttribute("liked");

        // automatic generator of news if there are less then 5 news in DB
        try {
            creatingMaterialsForTest();
        } catch (DBException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // adding new to database
        if (request.getMethod().equals("POST")) {
            try {
                newItemDAO.create(insertNewToDB(request));
            } catch (DBException e) {
                e.printStackTrace();
                return new MVCModel("/news.jsp", "Something gone wrong with DB.");
            }
        }

        // deleting new item by id
        deletingNewItem(request);

        // adding likes
        if (!likedItems.contains(request.getParameter("idLike"))) {
            likeNewItem(request);
            likedItems.add(request.getParameter("idLike"));
        }

        // passing news from DB to page
        MVCModel model = null;
        try {
            model = new MVCModel("/news.jsp", newItemDAO.getAll());
        } catch (DBException e) {
            e.printStackTrace();
        }

        return model;
    }

//---------------PROCEDURE DEFINATION-----------------------------------
    private void deletingNewItem(HttpServletRequest request) {
        if (request.getParameter("idDelete") != null) {
            String dateID = new String(request.getParameter("idDelete"));
            try {
                newItemDAO.delete(dateID);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    private void likeNewItem(HttpServletRequest request) {
        if (request.getParameter("idLike") != null) {
            String dateID = new String(request.getParameter("idLike"));
            try {
                newItemDAO.update(newItemDAO.getById(dateID));
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    private void creatingMaterialsForTest() throws DBException, InterruptedException {
        Random rand = new Random();
        if (newItemDAO.getAll().size() < 5) {
            for (int i = 0; i < 5; i++) {
                Timestamp stamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(stamp.getTime());
                NewItem newItem = new NewItem(formatter.format(date),
                        "Title-" + i, "This is a new number-" + i, rand.nextInt(100));

                    newItemDAO.create(newItem);

                    sleep(2000);
            }
        }
    }

    private NewItem insertNewToDB(HttpServletRequest request) {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());

        NewItem newItem = new NewItem(
                formatter.format(date),
                request.getParameter("title"),
                request.getParameter("body"),
                0);

        return newItem;
    }
}
