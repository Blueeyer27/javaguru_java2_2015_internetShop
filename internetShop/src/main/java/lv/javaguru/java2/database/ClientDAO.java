package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Client;

import java.util.List;

/**
 * Created by Anna on 05.02.15.
 */
public interface ClientDAO {

    void create(Client client) throws DBException;

    Client getById(Long id) throws DBException;

    void delete(Long id) throws DBException;

    void update(Client user) throws DBException;

    List<Client> getAll() throws DBException;

}
