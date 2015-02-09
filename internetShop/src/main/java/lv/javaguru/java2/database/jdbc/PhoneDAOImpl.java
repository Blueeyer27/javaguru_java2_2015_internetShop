package lv.javaguru.java2.database.jdbc;


import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.PhoneDAO;
import lv.javaguru.java2.domain.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna on 09.02.15.
 */
public class PhoneDAOImpl extends DAOImpl implements PhoneDAO {

    @Override
    public void create(Phone phone) throws DBException {
        if (phone == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into PHONES values (default, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, phone.getClientId());
            preparedStatement.setString(2, phone.getPhoneNumber());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                phone.setPhoneId(rs.getLong(1));
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
    public Phone getById(Long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from PHONES where PhoneId = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Phone phone = null;
            if (resultSet.next()) {
                phone = new Phone();
                phone.setPhoneId(resultSet.getLong("PhoneId"));
                phone.setClientId(resultSet.getLong("ClientId"));
                phone.setPhoneNumber(resultSet.getString("PhoneNumber"));
            }
            return phone;
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }


    public List<Phone> getAll() throws DBException {
        List<Phone> phones = new ArrayList<Phone>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from PHONES");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Phone phone = new Phone();
                phone.setPhoneId(resultSet.getLong("PhoneId"));
                phone.setClientId(resultSet.getLong("ClientId"));
                phone.setPhoneNumber(resultSet.getString("PhoneNumber"));
                phones.add(phone);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list UserDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return phones;
    }


    @Override
    public void delete(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from PHONES where PhoneId = ?");
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
    public void update(Phone phone) throws DBException {

        if (phone == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update PHONES set PhoneNumber = ?" +
                            "where PhoneId = ?");
            preparedStatement.setString(1, phone.getPhoneNumber());
            preparedStatement.setLong(2, phone.getPhoneId());
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
