package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.CartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.CartDB;
import lv.javaguru.java2.domain.Product;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 2015.03.04..
 */

@Component("ORM_CartDAO")
@Transactional
public class CartDAOImpl implements CartDAO {

    @Autowired
    @Qualifier("ORM_ProductDAO")
    ProductDAO product;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addElem(CartDB cart) throws DBException {
        Session session = sessionFactory.getCurrentSession();

//        System.out.println("cart: " + cart.getID() + cart.getProductId() + " " + cart.getCount() + " " + cart.getUserID() + " " + cart.getIsOrdered());
//         "username" - name of field (not table name)
        System.out.println("Searching!!!");
        CartDB cartDB = (CartDB) session.createCriteria(CartDB.class)
                .add(Restrictions.eq("userID", cart.getUserID()))
                .add(Restrictions.eq("productID", cart.getProductID()))
                .add(Restrictions.eq("isOrdered", cart.getIsOrdered())).uniqueResult();
//
//        System.out.println("cartDB: " + cartDB.getID() + " " + cartDB.getProductId() + " " + cartDB.getCount() + " " + cartDB.getUserID() + " " + cartDB.getIsOrdered());
//
        System.out.println("Check for null");
        if(cartDB != null) {
            cartDB.setCount(cartDB.getCount() + cart.getCount());
            session.update(cartDB);
        } else {
            System.out.println("Cart don't exist. Create new product.");
            session.persist(cart);
        }
    }

//    @Override
//    public void addElem(Long productID, Long userID, Integer count, boolean isOrdered) throws DBException {
//        throw new NotImplementedException();
//    }

    @Override
    public List<ProductInCart> getCartWithProd(Long userID) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        List<CartDB> carts = (List<CartDB>) session.createCriteria(CartDB.class)
                .add(Restrictions.eq("userID", userID)).list();

        for (CartDB cart : carts) {
            System.out.println("Product ID in cart: " + cart.getProductID());
        }

        if (carts != null) {
            List<ProductInCart> products = new ArrayList<ProductInCart>();
            for (CartDB cart : carts) {
                products.add(new ProductInCart(
                        cart.getProductID(),
                        cart.getCount(),
                        cart.getIsOrdered()))
                ;
            }

            return products;
        } else return null;
    }

    @Override
    public List<CartDB> getCart(Long userID) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return (List<CartDB>) session.createCriteria(CartDB.class)
                .add(Restrictions.eq("userID", userID)).list();
    }

    @Override
    public void removeFromCart(Product product, Long userID) {
        Session session = sessionFactory.getCurrentSession();

        CartDB cartDB = (CartDB) session.createCriteria(CartDB.class)
                .add(Restrictions.eq("userID", userID))
                .add(Restrictions.eq("productID", product))
                .add(Restrictions.eq("isOrdered", false)).uniqueResult();

        session.delete(cartDB);
    }
}
