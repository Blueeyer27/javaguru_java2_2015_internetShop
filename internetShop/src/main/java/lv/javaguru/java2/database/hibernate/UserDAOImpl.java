package lv.javaguru.java2.database.hibernate;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.UserDAO;
import lv.javaguru.java2.database.jdbc.DAOImpl;
import lv.javaguru.java2.domain.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component("ORM_UserDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void create(User user) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public User getById(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return (User) session.get(User.class, id);
    }

    @Override
    public User getByLogin(String login) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        // "username" - name of field (not table name)
        return (User) session.createCriteria(User.class).add(Restrictions.eq("username", login)).uniqueResult();
    }

    public List<User> getAll() throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(User.class).list();
    }

    @Override
    public void delete(Long id) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        session.delete(user);
    }

    @Override
    public void update(User user) throws DBException {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

}
