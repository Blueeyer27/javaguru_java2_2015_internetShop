package lv.javaguru.java2.database.jdbc;

import lv.javaguru.java2.database.CommentDAO;
import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.Comment;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 2015.03.01..
 */

@Component("JDBC_CommentDAO")
public class CommentDAOImpl extends DAOImpl implements CommentDAO {
    @Override
    public void create(Comment comment) throws DBException {
        if (comment == null) {
            return;
        }

        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into comments values (default, ?, ?, ?)",
                            PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setLong(2, comment.getUserID());
//            preparedStatement.setLong(3, comment.getProductID());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()){
                comment.setId(rs.getLong(1));
            }
        } catch (Throwable e) {
            System.out.println("Exception in CommentDAOImpl.create()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Comment getById(Long id) throws DBException {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Long id) throws DBException {
        throw new NotImplementedException();
    }

    @Override
    public void update(Comment comment) throws DBException {
        throw new NotImplementedException();
    }

    @Override
    public List<Comment> getAll(long productID) throws DBException {
        List<Comment> comments = new ArrayList<Comment>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from comments where ProductID = " + productID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                Comment comment = new Comment(
//                   resultSet.getLong("UserID"),
//                   resultSet.getLong("ProductID"),
//                   resultSet.getString("Comment")
//                );
//                comment.setId(resultSet.getLong("ID"));
//                comments.add(comment);
            }
        } catch (Throwable e) {
            System.out.println("Exception in CommentDAOImpl.getList()");
            e.printStackTrace();
            throw new DBException(e);
        } finally {
            closeConnection(connection);
        }
        return comments;
    }
}
