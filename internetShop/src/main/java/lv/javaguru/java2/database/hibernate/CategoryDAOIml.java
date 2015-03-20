package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.CategoryDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.jdbc.DAOImpl;
import lv.javaguru.java2.domain.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Anna on 18.03.15.
 */

@Component("ORM_CategoryDAO")
@Transactional
public class CategoryDAOIml implements CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Category category) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(category);
    }

    @Override
    public Category getById(String id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return (Category) session.get(Category.class, id);
    }

    @Override
    public List<Category> getAll() throws DBException {
        Session session = sessionFactory.getCurrentSession();

        return session.createCriteria(Category.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public void delete(long id) throws DBException {

    }

    @Override
    public void update(Category category) throws DBException {

    }



}
