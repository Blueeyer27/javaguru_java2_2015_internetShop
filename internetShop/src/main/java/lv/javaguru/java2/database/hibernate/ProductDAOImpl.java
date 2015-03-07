package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Anton on 2015.03.04..
 */

@Component("ORM_ProductDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Product product) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(product);
    }

    @Override
    public Product getById(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return (Product) session.get(Product.class, id);
    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Product product = (Product) session.get(Product.class, id);
        if (product != null)
            session.delete(product);
    }

    @Override
    public void update(Product product) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
    }

    @Override
    public List<Product> getRange(int startRow, int rowCount) throws DBException {
        Session session = sessionFactory.getCurrentSession();

        Integer productCount = ((Number) session.createCriteria(Product.class)
                .setProjection(Projections.rowCount()).uniqueResult()).intValue();

        System.out.println("Products total: " + productCount
                + " Start row: " + startRow + " Row count: " + rowCount);

        Integer difference = productCount - startRow - rowCount;

        if (difference < 10)
            if (productCount - startRow <= 0) return null;
            else return session.createCriteria(Product.class)
                    .setFirstResult((difference < 0) ? 0 : difference)
                    .setMaxResults(productCount - startRow).list();

        return session.createCriteria(Product.class)
                .setFirstResult((difference < 0) ? 0 : difference)
                .setMaxResults(rowCount).list();
    }

    @Override
    public List<Product> getAll() throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Product.class).list();
    }

    @Override
    public Long getTotal() throws DBException {
        Session session = sessionFactory.getCurrentSession();

        return ((Number) session.createCriteria(Product.class)
                .setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }
}
