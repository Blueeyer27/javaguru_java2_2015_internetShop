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

    public Client(String name, String surname, String phone, String eMail) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eMail = eMail;
    }

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Client() {
    }

    public Client(long id, String name, String surname, Address address, String eMail, String phone, Map<Integer, Booking> orders) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.eMail = eMail;
        this.phone = phone;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Map<Integer, Booking> getOrders() {
        return orders;
    }

    public void setOrders(Map<Integer, Booking> orders) {
        this.orders = orders;
    }
}
