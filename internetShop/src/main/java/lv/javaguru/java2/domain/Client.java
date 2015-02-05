package lv.javaguru.java2.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anna on 05.02.15.
 */
public class Client {
    private long id;
    private String name;
    private String surname;
    private Address address;
    private String eMail;
    private String phone;
    private Map<Integer, Booking> orders = new HashMap<Integer, Booking>(); //список заказов клиента
}
