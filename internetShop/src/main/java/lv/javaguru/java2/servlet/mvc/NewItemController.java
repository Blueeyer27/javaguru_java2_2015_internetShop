package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;

import lv.javaguru.java2.PasswordHash;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.NewItemDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.UserDAOImpl;
import lv.javaguru.java2.domain.NewItem;
import lv.javaguru.java2.domain.User;
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
import java.util.*;

import static java.lang.Thread.sleep;

/**
 * Created by Anna on 27.02.15.
 */

@Component
public class NewItemController implements MVCController {

    @Autowired
    private NewItemDAO newItemDAO;
    Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    public MVCModel processRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
        HttpSession session = request.getSession();
        session.setAttribute("page_name", "News");

        // automatic generator of news if there are less then 5 news in DB
        try {
            creatingMaterialsForTest();
        } catch (DBException e) {
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
        likeNewItem(request);


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

        private void deletingNewItem(HttpServletRequest request){
            if (request.getParameter("idDelete") != null) {
                String dateID = new String(request.getParameter("idDelete"));
                try {
                    newItemDAO.delete(dateID);
                } catch (DBException e) {
                    e.printStackTrace();
                }
            }
        }


        private void likeNewItem(HttpServletRequest request){
            if (request.getParameter("idLike") != null) {
                String dateID = new String(request.getParameter("idLike"));
                try {
                    newItemDAO.update(newItemDAO.getById(dateID));
                } catch (DBException e) {
                    e.printStackTrace();
                }
            }
        }


        private void creatingMaterialsForTest() throws DBException {
            Random rand = new Random();
            if(newItemDAO.getAll().size() < 5){
                for (int i = 0; i<5; i++){
                    Timestamp stamp = new Timestamp(System.currentTimeMillis());
                    Date date = new Date(stamp.getTime());
                    NewItem newItem = new NewItem(formatter.format(date), "Title-"+i, "This is a new number-"+i, rand.nextInt(100));
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
        }

        private NewItem insertNewToDB(HttpServletRequest request){
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
