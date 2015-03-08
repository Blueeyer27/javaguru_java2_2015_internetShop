package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.CartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.CartDB;
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
        CartDB cartDB = (CartDB) session.createCriteria(CartDB.class)
                .add(Restrictions.eq("userID", cart.getUserID()))
                .add(Restrictions.eq("productID", cart.getProductId()))
                .add(Restrictions.eq("isOrdered", cart.getIsOrdered())).uniqueResult();
//
//        System.out.println("cartDB: " + cartDB.getID() + " " + cartDB.getProductId() + " " + cartDB.getCount() + " " + cartDB.getUserID() + " " + cartDB.getIsOrdered());
//
        if(cartDB != null) {
            cartDB.setCount(cartDB.getCount() + 1);
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
    public List<ProductInCart> getCart(Long userID) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        List<CartDB> carts = (List<CartDB>) session.createCriteria(CartDB.class)
                .add(Restrictions.eq("userID", userID)).list();

        for (CartDB cart : carts) {
            System.out.println("Product ID in cart: " + cart.getProductId());
        }

        if (carts != null) {
            List<ProductInCart> products = new ArrayList<ProductInCart>();
            for (CartDB cart : carts) {
                products.add(new ProductInCart(
                        product.getById(cart.getProductId()),
                        cart.getCount(),
                        cart.getIsOrdered()))
                ;
            }

            return products;
        } else return null;
    }
}