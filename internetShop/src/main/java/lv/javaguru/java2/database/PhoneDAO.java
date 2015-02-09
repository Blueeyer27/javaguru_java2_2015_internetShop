package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Phone;

import java.util.List;

/**
 * Created by Anna on 05.02.15.
 */
public interface PhoneDAO {

    void create(Phone phone) throws DBException;

    Phone getById(Long id) throws DBException;

    void delete(Long id) throws DBException;

    void update(Phone phone) throws DBException;

    List<Phone> getAll() throws DBException;

}
