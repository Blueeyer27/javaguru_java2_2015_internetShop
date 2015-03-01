package lv.javaguru.java2.database;

import lv.javaguru.java2.database.DBException;
import lv.javaguru.java2.domain.NewItem;

import java.util.List;

/**
 * Created by Anna on 27.02.15.
 */
public interface NewItemDAO {

    void create(NewItem newItem)throws DBException;

    //NewItem getById()throws DBException;

    List<NewItem> getAll() throws DBException;

    void delete(String id) throws DBException;
}
