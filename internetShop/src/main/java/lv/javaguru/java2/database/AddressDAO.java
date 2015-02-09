package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Address;

import java.util.List;

/**
 * Created by Anna on 05.02.15.
 */
public interface AddressDAO {

    void create(Address address) throws DBException;

    Address getById(Long id) throws DBException;

    void delete(Long id) throws DBException;

    void update(Address address) throws DBException;

    List<Address> getAll() throws DBException;

}
