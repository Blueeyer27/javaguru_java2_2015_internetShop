package lv.javaguru.java2.servlet.mvc;

import com.sun.corba.se.impl.io.TypeMismatchException;
import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.NewItemDAO;
import lv.javaguru.java2.domain.Category;
import lv.javaguru.java2.domain.NewItem;
import lv.javaguru.java2.servlet.mvc.models.MVCModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

//@Component
//public class NewsController extends AccessController {
@Controller
public class NewsController {

    @Autowired
    @Qualifier("ORM_CategoryDAO")
    private CategoryDAO categoryDAO;

    @Autowired
    @Qualifier("ORM_NewItemDAO")
    private NewItemDAO newItemDAO;

    public class Result{
        private List<NewItem> news;
        private List<Category> categories;
        private List<NewItem> newsFromCategory;

        public Result() {
        }

        public List<NewItem> getNews() {
            return news;
        }

        public void setNews(List<NewItem> news) {
            this.news = news;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories(List<Category> categories) {
            this.categories = categories;
        }

        public List<NewItem> getNewsFromCategory() {
            return newsFromCategory;
        }

        public void setNewsFromCategory(List<NewItem> newsFromCategory) {
            this.newsFromCategory = newsFromCategory;
        }
    }



    Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Result result = new Result();

    public void setNewItemDAO(NewItemDAO newItemDAO) {
        this.newItemDAO = newItemDAO;
    }

    //    @Override
//    public MVCModel safeRequest(HttpServletRequest request, HttpServletResponse response) throws TypeMismatchException {
    @RequestMapping(value = "news", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) throws DBException {
        ModelAndView model = new ModelAndView("news");

        HttpSession session = request.getSession();
        session.setAttribute("page_name", "Shop News");

        ArrayList<Long> likedItems = (ArrayList<Long>) session.getAttribute("liked");
        //List<NewItem> allNews = newItemDAO.getAll();

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
                //return new MVCModel("/news.jsp", "Something gone wrong with DB.");
                return model.addObject("model", "Something gone wrong with DB.");
            }
        }

        // deleting new item by id
        removingNewItem(request);

        // adding likes
        likeNewItem(request, likedItems);



        // passing news from DB to page
        //MVCModel model = null;
        try {
            //result.setNews(newItemDAO.getAll());
            result.setNews(newItemDAO.getAll());
            result.setCategories(categoryDAO.getAll());
            List<NewItem> fromCat = viewCat(request, request.getParameter("idParam"));
            result.setNewsFromCategory(fromCat);
        } catch (DBException e) {
            e.printStackTrace();
        }

        try {
            //model = new MVCModel("/news.jsp", newItemDAO.getAll());
            //model.addObject("model", newItemDAO.getAll());
            model.addObject("model", result);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return model;
    }

    //---------------PROCEDURE DEFINATION-----------------------------------
    private void removingNewItem(HttpServletRequest request) {
        if (request.getParameter("idRemove") != null) {
            //String dateID = new String(request.getParameter("idDelete"));
            long num = Long.parseLong(request.getParameter("idRemove"));
            try {
                newItemDAO.delete(num);
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Long> likeNewItem(HttpServletRequest request, ArrayList<Long> likedItems) {
        if (request.getParameter("idLike") != null) {
            //String dateID = new String(request.getParameter("idLike"));
            long num = Long.parseLong(request.getParameter("idLike"));
            if (!likedItems.contains(num)) {
                try {
                    newItemDAO.update(newItemDAO.getById(num));
                    likedItems.add(num);
                } catch (DBException e) {
                    e.printStackTrace();
                }
            }
        }
        return likedItems;
    }

    private List<NewItem> viewCat(HttpServletRequest request, String param) throws DBException {
        List<NewItem> rez = newItemDAO.getAll();

        if ( (request.getParameter("idParam") == null) && ( (request.getParameter("idView") == null) || (request.getParameter("idView").equals("All")) ) ){
            rez.clear();
            rez = newItemDAO.getAll();
        } else if ( (request.getParameter("idParam") != null) && ((request.getParameter("idView") == null) || (request.getParameter("idView").equals("All")) ) ){
            rez.clear();
            rez = newItemDAO.getAll(param);
        } else if((request.getParameter("idParam") == null) && (request.getParameter("idView") != null)){
            String catName = request.getParameter("idView");
            rez.clear();
            rez = newItemDAO.getNewsFromCat(categoryDAO.getById(catName));

        } else if ( (request.getParameter("idParam") != null) && (request.getParameter("idView") != null) ){
            String catName = request.getParameter("idView");
            rez.clear();
            //rez = categoryDAO.getNews(catName);
            rez = newItemDAO.getNewsFromCat(categoryDAO.getById(catName), param);
            // (NewItem a : all){
            //if (a.getCategory().getCatName().equals(catName)){
            //rez.add(a);
            //}
            //}*/
        } else {
            rez = newItemDAO.getAll();
        }
        return rez;
    }

    private void creatingMaterialsForTest() throws DBException, InterruptedException {
        Random rand = new Random();
        if (newItemDAO.getAll().size() < 5) {
            for (int i = 0; i < 3; i++) {
                Timestamp stamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(stamp.getTime());
                NewItem newItem = new NewItem(categoryDAO.getById("Sales"), formatter.format(date),
                        "Title-Sales-" + i, "This is a new number-" + i, rand.nextInt(100));
                newItemDAO.create(newItem);
                sleep(2000);
            }
            for (int i = 3; i < 6; i++) {
                Timestamp stamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(stamp.getTime());
                NewItem newItem = new NewItem(categoryDAO.getById("Comming_Soon"), formatter.format(date),
                        "Title-Comming_Soon-" + i, "This is a new number-" + i, rand.nextInt(100));
                newItemDAO.create(newItem);
                sleep(2000);
            }

            for (int i = 6; i < 9; i++) {
                Timestamp stamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(stamp.getTime());
                NewItem newItem = new NewItem(categoryDAO.getById("Actual"), formatter.format(date),
                        "Title-Actual-" + i, "This is a new number-" + i, rand.nextInt(100));
                newItemDAO.create(newItem);
                sleep(2000);
            }
        }
    }

    private NewItem insertNewToDB(HttpServletRequest request) throws DBException {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());
        Category category = categoryDAO.getById(request.getParameter("category"));

        NewItem newItem = new NewItem(
                category,
                formatter.format(date),
                request.getParameter("title"),
                request.getParameter("body"),
                0);

        return newItem;
    }


}
