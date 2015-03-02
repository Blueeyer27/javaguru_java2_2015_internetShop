package lv.javaguru.java2.database;

import lv.javaguru.java2.domain.Cart;

import java.util.List;

/**
 * Created by Anton on 2015.03.02..
 */
public interface CartDAO {
    void addElem(Long productID, Long userID, Integer count, boolean isOrdered) throws DBException;
    Cart getCart(Long userID) throws DBException;
    //void updateElem(Long productID, Long userID, Integer toAdd) throws DBException;
}
