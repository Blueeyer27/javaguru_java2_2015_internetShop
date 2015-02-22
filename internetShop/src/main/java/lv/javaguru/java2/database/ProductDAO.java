package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Product;

import java.util.List;

/**
 * Created by Tanya
 */
public interface ProductDAO {

    void create(Product product) throws DBException;

    Product getById(Long id) throws DBException;

    void delete(Long id) throws DBException;

    void update(Product product) throws DBException;

    List<Product> getRange(int startRow, int rowCount) throws DBException;

    List<Product> getAll() throws DBException;

}
