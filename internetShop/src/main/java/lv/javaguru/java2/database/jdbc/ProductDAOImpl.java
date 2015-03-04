package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.Product;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanya
 */
@Component("JDBC_ProductDAO")
public class ProductDAOImpl extends DAOImpl implements ProductDAO {

    @Override
    public void create(Product product) throws DBException {
        if (product == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into products (Name, Description, Price, Picture) values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setString(4, product.getImage());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                product.setProductId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute ProductDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public Product getById(Long id) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from products where ProductID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getLong("ProductID"));
                product.setName(resultSet.getString("Name"));
                product.setDescription(resultSet.getString("Description"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setImage(resultSet.getString("Picture"));
            }
            return product;
        } catch (Throwable e) {
            System.out.println("Exception while execute ProductDAOImpl.getById()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    public List<Product> getAll() throws DBException {
        List<Product> products = new ArrayList<Product>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from products");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getLong("ProductID"));
                product.setName(resultSet.getString("Name"));
                product.setDescription(resultSet.getString("Description"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setImage(resultSet.getString("Picture"));
                products.add(product);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list ProductDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return products;
    }

    @Override
    public void delete(Long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from products where ProductID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ProductDAOImpl.delete()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void update(Product product) throws DBException {
        if (product == null) {
            return;
        }

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update products set Name = ?, Description = ?, Price = ?, Picture = ? " +
                            "where ProductID = ?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.setLong(5, product.getProductId());
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception while execute ProductDAOImpl.update()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public List<Product> getRange(int startRow, int rowCount) throws DBException {
        List<Product> products = new ArrayList<Product>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement
                    = connection.prepareStatement("SELECT * FROM products ORDER BY productID "
                    + "LIMIT " + rowCount + " OFFSET " + startRow);
            //preparedStatement.setInt(1, rowCount);
            //preparedStatement.setInt(2, startRow-1);


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getLong("ProductID"));
                product.setName(resultSet.getString("Name"));
                product.setDescription(resultSet.getString("Description"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setImage(resultSet.getString("Picture"));
                products.add(product);
            }
        } catch (Throwable e) {
            System.out.println("Exception while getting customer list ProductDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return products;
    }

}
