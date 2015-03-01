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
import java.security.NoSuchAlgorithmException;

import java.security.spec.InvalidKeySpecException;


import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created by Anna on 27.02.15.
 */

@Component
public class NewItemController implements MVCController {

    @Autowired
    private NewItemDAO newItemDAO;

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();
        session.setAttribute("page_name", "News");

        if (request.getParameter("id") != null) {
            String dateID = new String(request.getParameter("id"));
            try {
                newItemDAO.delete(dateID);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }

        try {
            if(newItemDAO.getAll().size() < 5){
                Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                for (int i = 0; i<5; i++){
                   Timestamp stamp = new Timestamp(System.currentTimeMillis());
                   Date date = new Date(stamp.getTime());
                   NewItem newItem = new NewItem(formatter.format(date), "Title-"+i, "This is a new number-"+i );
                   try {
                       newItemDAO.create(newItem);
                   } catch (DBException e) {
                       e.printStackTrace();
                   }
                   try {
                       sleep(2000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                }

            }
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (request.getMethod().equals("POST")) {
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(stamp.getTime());

            NewItem newItem = new NewItem(
                    formatter.format(date),
                    request.getParameter("title"),
                    request.getParameter("body"));


            try {
                newItemDAO.create(newItem);
            } catch (DBException e) {
                e.printStackTrace();
                return new MVCModel("/news.jsp", "Something gone wrong with DB.");
            }
        }


        MVCModel model = null;
        try {
            model = new MVCModel("/news.jsp", newItemDAO.getAll());
        } catch (DBException e) {
            e.printStackTrace();
        }
         return model;


        }




    }
