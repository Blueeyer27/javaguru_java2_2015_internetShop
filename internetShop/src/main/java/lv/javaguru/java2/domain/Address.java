package lv.javaguru.java2.domain;

/**
 * Created by Anna on 09.02.15.
 */
public class Address {
    private long addressId;
    private long clientId;
    private String country;
    private String city;  //название города или посёлка
    private String street;
    private int houseNo;
    private int flatNo;
    private String postcode; //почтовый индекс

    public Address() {
    }

    public Address(long addressId, long clientId, String country, String city, String street, int houseNo, int flatNo, String postcode) {
        this.addressId = addressId;
        this.clientId = clientId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.flatNo = flatNo;
        this.postcode = postcode;
    }

    public Address(long addressId, long clientId, String country, String city, String street, int houseNo, String postcode) {
        this.addressId = addressId;
        this.clientId = clientId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.postcode = postcode;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(int houseNo) {
        this.houseNo = houseNo;
    }

    public int getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(int flatNo) {
        this.flatNo = flatNo;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
