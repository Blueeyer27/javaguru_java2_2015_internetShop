package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.ProductInCartDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.ProductDAO;
import lv.javaguru.java2.domain.ProductInCart;
import lv.javaguru.java2.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Anton on 2015.03.02..
 */

@Component("JDBC_CartDAO")
public class ProductInCartDAOImpl extends DAOImpl implements ProductInCartDAO {
    @Autowired
    @Qualifier("ORM_ProductDAO")
    ProductDAO productDAO;

    //@Override
    public void addElem(Long productID, Long userID,
                        Integer count, boolean isOrdered) throws DBException {
        Connection connect = null;

        System.out.println("Checking if element exists in DB.");
        try {
            connect = getConnection();

            PreparedStatement prepStat = connect.prepareStatement("select * from productInCart " +
                    "where UserID = ? and ProductID = ? and IsOrdered = ?");
            prepStat.setLong(1, userID);
            prepStat.setLong(2, productID);
            prepStat.setBoolean(3, isOrdered);

            ResultSet resultSet = prepStat.executeQuery();

            //Long dbProductID, dbUserID;
            //Boolean dbIsOrdered;
            //boolean exist = false;
            //Integer dbCount = 0;

            if (resultSet.next()) {
                System.out.println("Element exist!");
                Integer dbCount = 0;
                //dbProductID = resultSet.getLong("ProductID");
                //dbUserID = resultSet.getLong("UserID");
                //dbIsOrdered = resultSet.getBoolean("IsOrdered");
                dbCount = resultSet.getInt("Count");

                count += dbCount;

                System.out.println(count);

                prepStat = connect
                        .prepareStatement("update CARTS set Count = ?" +
                                " where UserID = ? and ProductID = ? and IsOrdered = ?");
                prepStat.setInt(1, count);
                prepStat.setLong(2, userID);
                prepStat.setLong(3, productID);
                prepStat.setBoolean(4, isOrdered);

                prepStat.executeUpdate();
            } else {
                System.out.println("Element don't exist. Create new.");
                System.out.println(productID + " " + userID + " " + count + " " + isOrdered);
                prepStat =
                        connect.prepareStatement("insert into CARTS values " +
                                        "(?, ?, ?, ?)");
                prepStat.setLong(1, productID);
                prepStat.setLong(2, userID);
                prepStat.setInt(3, count);
                prepStat.setBoolean(4, isOrdered);

                prepStat.executeUpdate();
            }
        } catch (Throwable e) {
            System.out.println("Exception while execute UserDAOImpl.getByLogin()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connect);
        }
    }

    @Override
    public List<ProductInCart> getCartWithProd(Long userID) throws DBException {
        return null;
    }

    @Override
    public List<ProductInCart> getCart(Long userID) throws DBException {
        throw new NotImplementedException();
    }

    @Override
    public void removeFromCart(Product product, Long userID) {
        throw new NotImplementedException();
    }

    //@Override
    public void removeFromCart(Long prodID, Long userID) {
        throw new NotImplementedException();
    }

    @Override
    public void addElem(ProductInCart cart) throws DBException {
        throw new NotImplementedException();
    }

//    @Override
//    public Cart getCart(Long userID) throws DBException {
//        Connection connection = null;
//        List<ProductInCart> products = new ArrayList<ProductInCart>();
//        try {
//            connection = getConnection();
//            PreparedStatement preparedStatement
//                    = connection.prepareStatement("SELECT * FROM CARTS");
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                Product product = productDAO.getById(resultSet.getLong("ProductID"));
//                products.add(new ProductInCart(product,
//                        resultSet.getInt("Count"),
//                        resultSet.getBoolean("IsOrdered")));
//            }
//        } catch (Throwable e) {
//            System.out.println("Exception while getting customer list ProductDAOImpl.getList()");
//            e.printStackTrace();
//            throw new DBException(e);
//        } finally {
//            closeConnection(connection);
//        }
//
//        return new Cart(userID, products);
//    }
}
