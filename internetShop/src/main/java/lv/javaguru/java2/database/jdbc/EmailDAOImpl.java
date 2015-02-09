package lv.javaguru.java2.database.jdbc;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.EmailDAO;
import lv.javaguru.java2.domain.Email;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna on 09.02.15.
 */
public class EmailDAOImpl extends DAOImpl implements EmailDAO {

    @Override
    public void create(Email email) throws DBException {
        if (email == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into EMAILS values (default, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, email.getClientId());
            preparedStatement.setString(2, email.getEmailAddr());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                email.setEmailId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute ClientDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public Email getById(Long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from EMAILS where EmailId = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Email email = null;
            if (resultSet.next()) {
                email = new Email();
                email.setEmailId(resultSet.getLong("EmailId"));
                email.setClientId(resultSet.getLong("ClientId"));
                email.setEmailAddr(resultSet.getString("Email_Address"));
            }
            return email;
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }


    public List<Email> getAll() throws DBException {
        List<Email> emails = new ArrayList<Email>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from EMAILS");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Email email = new Email();
                email.setEmailId(resultSet.getLong("EmailId"));
                email.setClientId(resultSet.getLong("ClientId"));
                email.setEmailAddr(resultSet.getString("Email_Address"));
                emails.add(email);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list UserDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return emails;
    }


    @Override
    public void delete(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from EMAILS where EmailId = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public void update(Email email) throws DBException {

        if (email == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update EMAILS set Email_Address = ?" +
                            "where EmailId = ?");
            preparedStatement.setString(1, email.getEmailAddr());
            preparedStatement.setLong(2, email.getEmailId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ClientDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }

}
