package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Email;

import java.util.List;

/**
 * Created by Anna on 05.02.15.
 */
public interface EmailDAO {

    void create(Email email) throws DBException;

    Email getById(Long id) throws DBException;

    void delete(Long id) throws DBException;

    void update(Email email) throws DBException;

    List<Email> getAll() throws DBException;

}
