package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.CommentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Anton on 2015.03.13..
 */

@Component("ORM_CommentDAO")
@Transactional
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(Comment comment) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(comment);
    }

    @Override
    public Comment getById(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return (Comment) session.get(Comment.class, id);
    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        Comment comment = (Comment) session.get(Comment.class, id);
        if (comment != null)
            session.delete(comment);
    }

    @Override
    public void update(Comment comment) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.update(comment);
    }

    @Override
    public List<Comment> getAll(long productID) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Comment.class).addOrder(Order.desc("id")).list();
    }
}
