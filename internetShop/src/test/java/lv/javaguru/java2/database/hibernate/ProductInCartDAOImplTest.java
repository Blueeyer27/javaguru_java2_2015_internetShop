package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.DatabaseCleaner;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by Anton on 2015.03.15..
 */

@WebAppConfiguration
public class ProductInCartDAOImplTest extends SpringIntegration {
    @Autowired
    @Qualifier("ORM_UserDAO")
    private UserDAO userDAO;

    @Autowired
    @Qualifier("ORM_ProductDAO")
    private ProductDAO productDAO;

    @Autowired
    @Qualifier("ORM_ProductInCartDAO")
    private ProductInCartDAO productInCartDAO;

    private DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    @Before
    public void setUp() throws Exception {
        databaseCleaner.cleanDatabase();
    }

    @Test
    @Transactional
    public void testAddElem() throws Exception {
        Product product = new Product("Soda", "Cold tasty soda.", 0.59f);
        productDAO.create(product);

        User user = createUser();
        userDAO.create(user);

        int productCount = 8;
        ProductInCart productInCart =
                new ProductInCart(product, user.getId(), productCount, false);
        productInCartDAO.addElem(productInCart);

        assertTrue(productInCartDAO.getCart(user.getId()).size() == 1);

        productInCartDAO.addElem(productInCart);
        assertTrue(productInCartDAO
                .getCart(user.getId()).get(0).getCount() == productCount * 2);
        assertTrue(productInCartDAO.getCart(user.getId()).size() == 1);
    }

    @Test
    @Transactional
    public void testGetCartWithProd() throws Exception {
        User user = createUser();
        userDAO.create(user);


        assertTrue(productInCartDAO.getCartWithProd(user.getId()).size() == 0);

        Product product = new Product("Soda", "Cold tasty soda.", 0.59f);
        productDAO.create(product);

        int productCount = 8;
        ProductInCart productInCart =
                new ProductInCart(product, user.getId(), productCount, false);
        productInCartDAO.addElem(productInCart);

        assertTrue(productInCartDAO.getCartWithProd(user.getId()).size() == 1);
        assertEquals(productInCartDAO.getCartWithProd(user.getId())
                .get(0).getProduct().getProductId(), product.getProductId());
    }

    @Test
    @Transactional
    public void testRemoveFromCart() throws Exception {
        User user = createUser();
        userDAO.create(user);

        Product product = new Product("Soda", "Cold tasty soda.", 0.59f);
        productDAO.create(product);

        int productCount = 8;
        ProductInCart productInCart =
                new ProductInCart(product, user.getId(), productCount, false);
        productInCartDAO.addElem(productInCart);
        assertTrue(productInCartDAO.getCart(user.getId()).size() == 1);

        productInCartDAO.removeFromCart(product, user.getId());

        assertTrue(productInCartDAO.getCart(user.getId()).size() == 0);
    }

    private User createUser() {
        return new User("Iosif", "Petrov", "male",
                "+89612059407", "iosif,petrov@gmail.com",
                "iosi4ka", "parolik", 2);
    }
}
