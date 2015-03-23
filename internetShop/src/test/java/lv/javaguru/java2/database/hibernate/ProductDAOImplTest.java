package lv.javaguru.java2.database.hibernate;

import javafx.util.Pair;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.jdbc.DatabaseCleaner;
import lv.javaguru.java2.domain.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Anton on 2015.03.15..
 */

@WebAppConfiguration
public class ProductDAOImplTest extends SpringIntegration {

    @Autowired
    @Qualifier("ORM_ProductDAO")
    private ProductDAO productDAO;

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Before
    public void setUp() throws Exception {
        databaseCleaner.cleanDatabase();
    }

//    @After
//    public void tearDown() throws Exception {
//        databaseCleaner.cleanDatabase();
//    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Product product = createProduct();
        productDAO.create(product);

        assertNotNull(product.getProductId());
    }

    @Test
    @Transactional
    public void testGetById() throws Exception {
        Product product = createProduct();
        productDAO.create(product);

        assertNotNull(productDAO.getById(product.getProductId()));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        Product product = createProduct();
        productDAO.create(product);

        Long productID = product.getProductId();
        productDAO.delete(productID);

        assertNull(productDAO.getById(productID));
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Product product = createProduct();
        productDAO.create(product);

        String oldName = product.getName();
        Long productID = product.getProductId();

        product.setName("NEW_NAME");
        productDAO.update(product);

        assertNotNull(productDAO.getById(productID));
        assertNotEquals(oldName, productDAO.getById(productID).getName());
    }

    @Test
    @Transactional
    public void testGetRange() throws Exception {
        List<Product> newProducts = generateProducts(10);
        for (Product product : newProducts) {
            productDAO.create(product);
            assertNotNull(product.getProductId());
        }

        int startRow = 4,
            rowCount = 3;

        List<Product> dbProducts = productDAO.getRange(startRow, rowCount);
        for (Product product : dbProducts)
            System.out.println(product.getName());

        for (int i = startRow - 1, j = 0;
             i < (startRow + rowCount) && i <= dbProducts.size(); i++, j++) {
            Product dbProduct = dbProducts.get(j);
            Product product = newProducts.get(i);

            assertEquals(dbProduct.getProductId(), product.getProductId());
            assertEquals(dbProduct.getName(), product.getName());
        }

        assertNull(productDAO.getRange(100,5));
        assertEquals(productDAO.getRange(0, 100).size(), newProducts.size());
    }

    @Test
    @Transactional
    public void testGetAll() throws Exception {
        List<Product> newProducts = generateProducts(15);
        for (Product product : newProducts) {
            productDAO.create(product);
            assertNotNull(product.getProductId());
        }

        List<Product> dbProducts = productDAO.getAll();

        assertEquals(dbProducts.size(), newProducts.size());

        Iterator<Product> newProd = newProducts.iterator();
        Iterator<Product> dbProd = dbProducts.iterator();
        while(newProd.hasNext() && dbProd.hasNext())
        {
            assertEquals(newProd.next().getProductId(), dbProd.next().getProductId());
        }
    }


    @Test
    @Transactional
    public void testGetTotal() throws Exception {
        List<Product> newProducts = generateProducts(27);
        for (Product product : newProducts) {
            productDAO.create(product);
            assertNotNull(product.getProductId());
        }
        Long totalProducts = Long.valueOf(newProducts.size());
        assertEquals(totalProducts, productDAO.getTotal());
    }

    private Product createProduct() {
        return new Product("Banana", "Tasty yellow fruit.", 15.3f);
    }

    private List<Product> generateProducts(int productCount) {
        List<Product> products = new ArrayList<Product>();
        for (int i = 1; i <= productCount; i++) {
            Product product = new Product("productName" + i,
                    "productDescription" + i, 15.99f);
            products.add(product);
        }

        return products;
    }
}
