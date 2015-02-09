package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ProductDAOImplTest {

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    private ProductDAOImpl productDAO = new ProductDAOImpl();


    @Before
    public void init() throws DBException {
        databaseCleaner.cleanDatabase();
    }

    @Test
    public void testCreate() throws DBException {
        Product product = createProduct("Nokia","The best phone ever!", 180.00f);

        productDAO.create(product);

        Product productFromDB = productDAO.getById(product.getProductId());
        assertNotNull(productFromDB);
        assertEquals(product.getProductId(), productFromDB.getProductId());
        assertEquals(product.getName(), productFromDB.getName());
        assertEquals(product.getDescription(), productFromDB.getDescription());
        assertEquals(product.getPrice(), productFromDB.getPrice(), 0.0001);
    }


    private Product createProduct(String name, String description, float price) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

}