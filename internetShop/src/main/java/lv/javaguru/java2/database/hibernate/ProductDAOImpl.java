package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
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

        ProjectionList productProj = Projections.projectionList();

        for (String prop : sessionFactory.getClassMetadata(Product.class)
                .getPropertyNames()) {
            System.out.println("Property: " + prop);
            productProj.add(Projections.alias(Projections.property(prop), prop));
        }

        if (difference < 1)
            if (productCount - startRow <= 0) return null;
//            else return session.createCriteria(Product.class)
//                    .addOrder(Order.asc("productId"))
//                    .setFirstResult((difference < 0) ? 0 : difference)
//                    .setMaxResults(productCount - startRow)
//                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            else return session.createCriteria(Product.class)
                    .setProjection(productProj
                            .add(Projections.property("productId"), "productId")
                            .add(Projections.property("description"), "description"))
                    .setFirstResult((difference < 0) ? 0 : difference)
                    .setMaxResults(productCount - startRow)
                    .addOrder(Order.asc("productId"))
                    .setResultTransformer(Transformers.aliasToBean(Product.class)).list();

//        return session.createCriteria(Product.class, "productID")
//                .addOrder(Order.asc("productId"))
//                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
//                .setFirstResult((difference < 0) ? 0 : difference)
//                .setMaxResults(rowCount)
//                .list();

        return session.createCriteria(Product.class)
                .setProjection(productProj
                        .add(Projections.property("productId"), "productId")
                        .add(Projections.property("description"), "description"))
                .setFirstResult((difference < 0) ? 0 : difference)
                .setMaxResults(rowCount)
                .addOrder(Order.asc("productId"))
                .setResultTransformer(Transformers.aliasToBean(Product.class)).list();
    }

    @Override
    public List<Product> getAll() throws DBException {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Product.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.asc("productId")).list();
    }

    @Override
    public Long getTotal() throws DBException {
        Session session = sessionFactory.getCurrentSession();

        return ((Number) session.createCriteria(Product.class)
                .setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }
}
