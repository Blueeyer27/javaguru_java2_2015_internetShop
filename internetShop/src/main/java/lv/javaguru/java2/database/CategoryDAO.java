package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Category;

import java.util.List;

/**
 * Created by Anna on 18.03.15.
 */
public interface CategoryDAO {

    void create(Category category)throws DBException;

    Category getById(String id)throws DBException;

    List<Category> getAll() throws DBException;

    void delete(long id) throws DBException;

    void update (Category category) throws DBException;

}
