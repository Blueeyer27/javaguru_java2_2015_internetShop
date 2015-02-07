package lv.javaguru.java2.domain;

/**
 * Created by AZ-03 on 07.02.15.
 */
import java.util.*;

public class Zakupka {
    private long zakupkaId;
    private long productId;
    private long skladId;
    private Date dateZakupka;
    private int quantity;

    public Zakupka(long zakupkaId, long productId, long skladId, Date dateZakupka, int quantity) {
        this.zakupkaId = zakupkaId;
        this.productId = productId;
        this.skladId = skladId;
        this.dateZakupka = dateZakupka;
        this.quantity = quantity;
    }

    public long getZakupkaID() {
        return zakupkaId;
    }

    public void setZakupkaId(long zakupkaId) {
        this.zakupkaId = zakupkaId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId){
        this.productId =  productId;
    }

    public long getSkladId() {
        return skladId;
    }

    public void setSkladId(long skladId) {
        this.skladId = skladId;
    }

    public Date getDateZakupka() {
        return dateZakupka;
    }

    public void setDateZakupka(Date dateZakupka) {
        this.dateZakupka = dateZakupka;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

