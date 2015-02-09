package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.AddressDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna on 09.02.15.
 */
public class AddressDAOImpl extends DAOImpl implements AddressDAO {
    @Override
    public void create(Address address) throws DBException {
        if (address == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into ADDRESSES values (default, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, address.getClientId());
            preparedStatement.setString(2, address.getCountry());
            preparedStatement.setString(3, address.getCity());
            preparedStatement.setString(4, address.getStreet());
            preparedStatement.setLong(5, address.getHouseNo());
            preparedStatement.setLong(6, address.getFlatNo());
            preparedStatement.setString(7, address.getPostcode());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                address.setAddressId(rs.getLong(1));
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
    public Address getById(Long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from ADDRESSES where AddressId = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Address address = null;
            if (resultSet.next()) {
                address = new Address();
                address.setAddressId(resultSet.getLong("AddressId"));
                address.setClientId(resultSet.getLong("ClientId"));
                address.setCountry(resultSet.getString("Country"));
                address.setCity(resultSet.getString("City"));
                address.setStreet(resultSet.getString("Street"));
                address.setHouseNo(resultSet.getInt("House"));
                address.setFlatNo(resultSet.getInt("Flat"));
                address.setPostcode(resultSet.getString("Postcode"));
            }
            return address;
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }


    public List<Address> getAll() throws DBException {
        List<Address> addresses = new ArrayList<Address>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from ADDRESSES");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Address address = new Address();
                address.setAddressId(resultSet.getLong("AddressId"));
                address.setClientId(resultSet.getLong("ClientId"));
                address.setCountry(resultSet.getString("Country"));
                address.setCity(resultSet.getString("City"));
                address.setStreet(resultSet.getString("Street"));
                address.setHouseNo(resultSet.getInt("House"));
                address.setFlatNo(resultSet.getInt("Flat"));
                address.setPostcode(resultSet.getString("Postcode"));
                addresses.add(address);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list UserDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return addresses;
    }


    @Override
    public void delete(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from ADDRESSES where AddressId = ?");
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
    public void update(Address address) throws DBException {

        if (address == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update ADDRESSES set City = ?" +
                            "where AddressId = ?");
            preparedStatement.setString(1, address.getCity());
            preparedStatement.setLong(2, address.getAddressId());
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
