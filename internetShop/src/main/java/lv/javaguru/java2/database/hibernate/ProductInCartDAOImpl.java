package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 2015.03.04..
 */

@Component("ORM_ProductInCartDAO")
@Transactional
public class ProductInCartDAOImpl implements ProductInCartDAO {

    //@Autowired
    //@Qualifier("ORM_ProductDAO")
    //ProductDAO product;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addElem(ProductInCart cart) throws DBException {
        Session session = sessionFactory.getCurrentSession();

//        System.out.println("cart: " + cart.getID() + cart.getProductId() + " " + cart.getCount() + " " + cart.getUserID() + " " + cart.getIsOrdered());
//         "username" - name of field (not table name)
        System.out.println("Searching!!!");
        ProductInCart productInCart = (ProductInCart) session.createCriteria(ProductInCart.class)
                .add(Restrictions.eq("userID", cart.getUserID()))
                .add(Restrictions.eq("product", cart.getProduct()))
                .add(Restrictions.eq("isOrdered", cart.getIsOrdered())).uniqueResult();
//
//        System.out.println("productInCart: " + productInCart.getID() + " " + productInCart.getProductId() + " " + productInCart.getCount() + " " + productInCart.getUserID() + " " + productInCart.getIsOrdered());
//
        System.out.println("Check for null");
        if(productInCart != null) {
            productInCart.setCount(productInCart.getCount() + cart.getCount());
            session.update(productInCart);
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
        List<ProductInCart> carts = (List<ProductInCart>) session.createCriteria(ProductInCart.class)
                .add(Restrictions.eq("userID", userID)).list();

        for (ProductInCart cart : carts) {
            System.out.println("Product ID in cart: " + cart.getProduct());
        }

        if (carts != null) {
            List<ProductInCart> products = new ArrayList<ProductInCart>();
            for (ProductInCart cart : carts) {
                products.add(cart);
            }

            return products;
        } else return null;
    }

    @Override
    public List<ProductInCart> getCart(Long userID) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return (List<ProductInCart>) session.createCriteria(ProductInCart.class)
                .add(Restrictions.eq("userID", userID)).list();
    }

    @Override
    public void removeFromCart(Product product, Long userID) {
        Session session = sessionFactory.getCurrentSession();

        ProductInCart productInCart = (ProductInCart) session.createCriteria(ProductInCart.class)
                .add(Restrictions.eq("userID", userID))
                .add(Restrictions.eq("product", product))
                .add(Restrictions.eq("isOrdered", false)).uniqueResult();

        session.delete(productInCart);
    }
}
