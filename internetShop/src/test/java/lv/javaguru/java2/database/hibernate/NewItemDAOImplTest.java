package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.NewItemDAO;
import lv.javaguru.java2.database.jdbc.DatabaseCleaner;
import lv.javaguru.java2.domain.NewItem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Anna on 17.03.15.
 */
public class NewItemDAOImplTest extends SpringIntegration {
    @Autowired
    @Qualifier("ORM_NewItemDAO")
    private NewItemDAO newItemDAO;

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Before
    public void setUp() throws Exception {
        databaseCleaner.cleanDatabase();
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        NewItem newItem = new NewItem("date", "Title", "Body", 150);
        newItemDAO.create(newItem);

        assertNotNull(newItem.getDateID());
    }

}
