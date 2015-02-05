package lv.javaguru.java2.domain;

/**
 * Created by Anna on 05.02.15.
 */
public class Address {
    private String country;
    private String city;
    private String region; //край (novads)
    private String parish; //волость (pagasts)
    private String settlement; //посёлок (ciems)
    private String street;
    private int houseNo;
    private int houseUnit; //корпус дома
    private int houseName; //название или буква дома
    private int flatNo;
    private String postcode; //почтовый индекс

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
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

    public int getHouseUnit() {
        return houseUnit;
    }

    public void setHouseUnit(int houseUnit) {
        this.houseUnit = houseUnit;
    }

    public int getHouseName() {
        return houseName;
    }

    public void setHouseName(int houseName) {
        this.houseName = houseName;
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

    public Address() {
    }

    public Address(String country, String city, String street, int houseNo, int flatNo, String postcode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.flatNo = flatNo;
        this.postcode = postcode;
    }

    public Address(String country, String city, String street, int houseNo, int houseUnit, int flatNo, String postcode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.houseUnit = houseUnit;
        this.flatNo = flatNo;
        this.postcode = postcode;
    }

    public Address(String country, String city, String street, int houseNo, int houseUnit, int houseName, int flatNo, String postcode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.houseUnit = houseUnit;
        this.houseName = houseName;
        this.flatNo = flatNo;
        this.postcode = postcode;
    }

    public Address(String country, String region, String parish, String settlement, String street, int houseNo, int houseUnit, int houseName, int flatNo, String postcode) {
        this.country = country;
        this.region = region;
        this.parish = parish;
        this.settlement = settlement;
        this.street = street;
        this.houseNo = houseNo;
        this.houseUnit = houseUnit;
        this.houseName = houseName;
        this.flatNo = flatNo;
        this.postcode = postcode;
    }

    public Address(String country, String region, String parish, String settlement, String street, int houseNo, int flatNo, String postcode) {
        this.country = country;
        this.region = region;
        this.parish = parish;
        this.settlement = settlement;
        this.street = street;
        this.houseNo = houseNo;
        this.flatNo = flatNo;
        this.postcode = postcode;
    }

    public Address(String country, String region, String parish, String settlement, String street, int houseNo, String postcode) {
        this.country = country;
        this.region = region;
        this.parish = parish;
        this.settlement = settlement;
        this.street = street;
        this.houseNo = houseNo;
        this.postcode = postcode;
    }

    public Address(String country, String region, String parish, String settlement, int houseName, String postcode) {
        this.country = country;
        this.region = region;
        this.parish = parish;
        this.settlement = settlement;
        this.houseName = houseName;
        this.postcode = postcode;
    }
}
