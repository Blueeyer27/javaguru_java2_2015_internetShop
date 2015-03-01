package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Comment;

import java.util.List;

/**
 * Created by Anton on 2015.03.01..
 */
public interface CommentDAO {
    void create(Comment comment) throws DBException;

    Comment getById(Long id) throws DBException;

    void delete(Long id) throws DBException;

    void update(Comment comment) throws DBException;

    List<Comment> getAll(long productID) throws DBException;
}
