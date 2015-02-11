package lv.javaguru.java2.domain;

/**
 * Created by AZ-03 on 07.02.15.
 */
import java.util.*;

public class Zakupka {
    private long ZakupkaID;
    private long ProductID;
    private long SkladID;
    private Date DateZakupka;
    private int Quantity;

    public Zakupka(long ZakupkaID, long ProductID, long SkladID, Date DateZakupka, int Quantity) {
        this.ZakupkaID = ZakupkaID;
        this.ProductID = ProductID;
        this.SkladID = SkladID;
        this.DateZakupka = DateZakupka;
        this.Quantity = Quantity;
    }

    public long getZakupkaID() {
        return ZakupkaID;
    }

    public void setZakupkaID(long ZakupkaID) {
        this.ZakupkaID = ZakupkaID;
    }

    public long getProductID() {
        return ProductID;
    }

    public void setProductID(long ProductID){
        this.ProductID =  ProductID;
    }

    public long getSkladID() {
        return SkladID;
    }

    public void setSkladID(long SkladID) {
        this.SkladID = SkladID;
    }

    public Date getDateZakupka() {
        return DateZakupka;
    }

    public void setDateZakupka(Date DateZakupka) {
        this.DateZakupka = DateZakupka;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
}

