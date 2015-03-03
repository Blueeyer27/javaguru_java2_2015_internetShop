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
//        Connection connection = null;
//
//        try {
//            connection = getConnection();
//            PreparedStatement preparedStatement = connection
//                    .prepareStatement("select * from USERS where UserId = ?");
//            preparedStatement.setLong(1, id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            User user = null;
//            if (resultSet.next()) {
//                user = new User();
//                user.setId(resultSet.getLong("UserId"));
//                user.setLogin(resultSet.getString("Login"));
//                user.setPassword(resultSet.getString("Password"));
//                user.setName(resultSet.getString("Name"));
//                user.setSurname(resultSet.getString("Surname"));
//                user.setGender(resultSet.getString("Gender"));
//                user.setPhone(resultSet.getString("Phone"));
//                user.setEmail(resultSet.getString("Email"));
//                user.setAccessLevel(resultSet.getInt("Access_Level"));
//            }
//            return user;
//        } catch (Throwable e) {
//            System.out.println("Exception while execute UserDAOImpl.getById()");
//            e.printStackTrace();
//            throw new DBException(e);
//        } finally {
//            closeConnection(connection);
//        }
        return new User();
    }

    @Override
    public User getByLogin(String login) throws DBException {
//        Connection connect = null;
//
//        System.out.println("getByLogin: " + login);
//        try {
//            connect = getConnection();
//
//            PreparedStatement prepStat = connect.prepareStatement("select * from USERS where Login = ?");
//            prepStat.setString(1, login);
//
//            ResultSet resultSet = prepStat.executeQuery();
//            User user = null;
//            if (resultSet.next()) {
//                user = new User();
//                user.setId(resultSet.getLong("UserId"));
//                user.setLogin(resultSet.getString("Login"));
//                user.setPassword(resultSet.getString("Password"));
//                user.setName(resultSet.getString("Name"));
//                user.setSurname(resultSet.getString("Surname"));
//                user.setGender(resultSet.getString("Gender"));
//                user.setPhone(resultSet.getString("Phone"));
//                user.setEmail(resultSet.getString("Email"));
//                user.setAccessLevel(resultSet.getInt("Access_Level"));
//            }
//            return user;
//        } catch (Throwable e) {
//            System.out.println("Exception while execute UserDAOImpl.getByLogin()");
//            e.printStackTrace();
//            throw new DBException(e);
//        } finally {
//            closeConnection(connect);
//        }

        return new User();
    }

    public List<User> getAll() throws DBException {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(User.class).list();
    }

    @Override
    public void delete(Long id) throws DBException {
//        Connection connection = null;
//        try {
//            connection = getConnection();
//            PreparedStatement preparedStatement = connection
//                    .prepareStatement("delete from USERS where UserId = ?");
//            preparedStatement.setLong(1, id);
//            preparedStatement.executeUpdate();
//        } catch (Throwable e) {
//            System.out.println("Exception while execute UserDAOImpl.delete()");
//            e.printStackTrace();
//            throw new DBException(e);
//        } finally {
//            closeConnection(connection);
//        }
    }

    @Override
    public void update(User user) throws DBException {
//        if (user == null) {
//            return;
//        }
//
//        Connection connection = null;
//        try {
//            connection = getConnection();
//            PreparedStatement preparedStatement = connection
//                    .prepareStatement("update USERS set Surname = ?" +
//                            "where UserId = ?");
//            preparedStatement.setString(1, user.getSurname());
//            preparedStatement.setLong(2, user.getId());
//            preparedStatement.executeUpdate();
//        } catch (Throwable e) {
//            System.out.println("Exception while execute UserDAOImpl.update()");
//            e.printStackTrace();
//            throw new DBException(e);
//        } finally {
//            closeConnection(connection);
//        }
    }

}
