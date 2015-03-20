package lv.javaguru.java2.database.hibernate;
        import lv.javaguru.java2.database.DBException;
        import lv.javaguru.java2.database.NewItemDAO;
        import lv.javaguru.java2.database.jdbc.DAOImpl;
        import lv.javaguru.java2.domain.NewItem;
        import org.hibernate.Session;
        import org.hibernate.criterion.Order;
        import org.springframework.stereotype.Component;

        import javax.transaction.Transactional;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.util.ArrayList;
        import java.util.List;
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.criterion.Restrictions;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;
        import javax.transaction.Transactional;

@Component("ORM_NewItemDAO")
@Transactional
public class NewItemDAOImpl implements NewItemDAO {

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void create(NewItem newItem) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(newItem);
    }

    @Override
    public List<NewItem> getAll() throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(NewItem.class).addOrder(Order.desc("num")).list();
    }

    @Override
    public void delete(long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        NewItem n = (NewItem) session.get(NewItem.class, id);
        session.delete(n);
    }

    @Override
    public NewItem getById(long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return (NewItem) session.get(NewItem.class, id);
    }

    @Override
    public void update(NewItem newItem) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        newItem.setLikes(newItem.getLikes() + 1);
        session.update(newItem);
    }





}

