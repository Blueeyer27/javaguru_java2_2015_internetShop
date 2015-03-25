package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.NewItemDAO;
import lv.javaguru.java2.database.jdbc.DatabaseCleaner;

import lv.javaguru.java2.domain.NewItem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@WebAppConfiguration
public class NewItemDAOImplTest extends SpringIntegration {
    @Autowired
    @Qualifier("ORM_NewItemDAO")
    private NewItemDAO newItemDAO;

    @Autowired
    @Qualifier("ORM_CategoryDAO")
    private CategoryDAO categoryDAO;

    Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Before
    public void setUp() throws Exception {
        databaseCleaner.cleanDatabase();
    }




    /*
    + void create(NewItem newItem)throws DBException;

    + NewItem getById(long id)throws DBException;

    !- List<NewItem> getAll() throws DBException;

    List<NewItem> getAll(String param) throws DBException;

    + void delete(long id) throws DBException;

    + void update (NewItem newItem) throws DBException;

    !- List<NewItem> getNewsFromCat(Category category, String param) throws DBException;

    !- List<NewItem> getNewsFromCat(Category category) throws DBException; */



    @Test
    @Transactional
    public void testCreate() throws Exception {
        NewItem newItem = createNew();
        newItemDAO.create(newItem);
        assertNotNull(newItem.getDateID());
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        NewItem nw = createNew();
        newItemDAO.create(nw);

        NewItem nwDB = newItemDAO.getById(nw.getNum());
        assertNotNull(nwDB);

        assertEquals(nw.getDateID(), nwDB.getDateID());
        assertEquals(nw.getNum(), nwDB.getNum());
        assertEquals(nw.getBody(), nwDB.getBody());
        assertEquals(nw.getTitle(), nwDB.getTitle());
        assertEquals(nw.getCategory(), nwDB.getCategory());
        assertEquals(nw.getLikes(), nwDB.getLikes());
    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        /*List<NewItem> news = createNews();

        sortList(news);

        List<NewItem> dbnews = newItemDAO.getAll();
        assertEquals(dbnews.size(), news.size());

        Iterator<NewItem> nw = news.iterator();
        Iterator<NewItem> dbnw = dbnews.iterator();
        while(nw.hasNext() && dbnw.hasNext())
        {
            assertEquals(nw.next().getDateID(), dbnw.next().getDateID());
        }*/
    }



    @Test
    @Transactional
    public void testDelete() throws Exception {
        NewItem nw = createNew();
        newItemDAO.create(nw);

        NewItem nwDB = newItemDAO.getById(nw.getNum());
        assertNotNull(nwDB);

        newItemDAO.delete(nwDB.getNum());
        nwDB = newItemDAO.getById(nw.getNum());
        assertNull(nwDB);
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        NewItem nw = createNew();
        newItemDAO.create(nw);

        assertNotNull(newItemDAO.getById(nw.getNum()));

        int likes = nw.getLikes() + 1;
        newItemDAO.update(nw);
        NewItem nwDB = newItemDAO.getById(nw.getNum());
        assertNotNull(nwDB);
        assertEquals(likes, nwDB.getLikes());
    }

    @Test
    @Transactional
    public void testGetNewsFromCat() throws DBException, InterruptedException {
        /*List<NewItem> nws = createNews();
        for(NewItem nw : nws){
            newItemDAO.create(nw);
        }
        List<NewItem> nwsDB = newItemDAO.getNewsFromCat(nws.get(0).getCategory());

        assertEquals(nws.size(), nwsDB.size());
        Iterator<NewItem> nw = nws.iterator();
        Iterator<NewItem> dbnw = nwsDB.iterator();
        while(nw.hasNext() && dbnw.hasNext())
        {
            assertEquals(nw.next().getDateID(), dbnw.next().getDateID());
        }*/
    }

    @Test
    @Transactional
    public void testGetNewsFromCatParamLast() throws DBException, InterruptedException {
        /*List<NewItem> nws = createNews();
        for(NewItem nw : nws){
            newItemDAO.create(nw);
        }
        List<NewItem> nwsDB = newItemDAO.getNewsFromCat(nws.get(0).getCategory(), "last");
        sortList(nws);

        assertEquals(nws.size(), nwsDB.size());
        Iterator<NewItem> nw = nws.iterator();
        Iterator<NewItem> dbnw = nwsDB.iterator();
        while(nw.hasNext() && dbnw.hasNext())
        {
            assertEquals(nw.next().getDateID(), dbnw.next().getDateID());
        }*/
    }

    @Test
    @Transactional
    public void testGetNewsFromCatParamFirst() throws DBException, InterruptedException {
        /*List<NewItem> nws = createNews();
        for(NewItem nw : nws){
            newItemDAO.create(nw);
        }
        List<NewItem> nwsDB = newItemDAO.getNewsFromCat(nws.get(0).getCategory(), "first");

        assertEquals(nws.size(), nwsDB.size());
        Iterator<NewItem> nw = nws.iterator();
        Iterator<NewItem> dbnw = nwsDB.iterator();
        while(nw.hasNext() && dbnw.hasNext())
        {
            assertEquals(nw.next().getDateID(), dbnw.next().getDateID());
        }*/
    }




    private NewItem createNew () throws DBException {
        Random rand = new Random();
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());
        NewItem newItem = new NewItem(categoryDAO.getById("Sales"), formatter.format(date),
                "Title-Test", "This is a test new" , rand.nextInt(100));
        return newItem;
    }

    private List<NewItem> createNews() throws DBException, InterruptedException {
        List<NewItem> news = new ArrayList<NewItem>();
        Random rand = new Random();

        for (int i = 1; i <= 3; i++) {
            Timestamp stamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(stamp.getTime());
            NewItem newItem = new NewItem(categoryDAO.getById("Sales"), formatter.format(date),
                    "Title-Sales-" + i, "This is a new number-" + i, rand.nextInt(100));
            newItemDAO.create(newItem);
            sleep(2000);
            news.add(newItem);
            assertNotNull(newItem.getDateID());
        }
        return news;
    }

    private void sortList(List<NewItem> nws){
        Collections.sort(nws, new Comparator<NewItem>() {
            @Override
            public int compare(NewItem o1, NewItem o2) {
                String o1Num = String.valueOf(o1.getNum());
                String o2Num = String.valueOf(o2.getNum());
                return o2Num.compareTo(o1Num);
            }
        });
    }

}
