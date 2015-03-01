package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.database.NewItemDAO;
import lv.javaguru.java2.domain.NewItem;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna on 27.02.15.
 */

@Component
public class NewItemDAOImpl extends DAOImpl implements NewItemDAO {


    @Override
    public void create(NewItem newItem) throws DBException {

        if (newItem == null){
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into News values (?, ?, ?)");
            preparedStatement.setString(1, newItem.getDateID());
            preparedStatement.setString(2, newItem.getTitle());
            preparedStatement.setString(3, newItem.getBody());

            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }

    }


    @Override
    public List<NewItem> getAll() throws DBException {
        List<NewItem> news = new ArrayList<NewItem>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from news");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                NewItem newItem = new NewItem();
                newItem.setDateID(resultSet.getString("DateId"));
                newItem.setTitle(resultSet.getString("Title"));
                newItem.setBody(resultSet.getString("Body"));
                news.add(newItem);
            }
        } catch (Throwable e) {
            System.out.println("Exception");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return news;
    }

    @Override
    public void delete(String id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from news where DateID = ?");
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (Throwable e) {
            System.out.println("Exception");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }


}
