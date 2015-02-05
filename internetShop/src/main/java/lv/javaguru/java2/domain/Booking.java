package lv.javaguru.java2.domain;

import java.util.Date;

/**
 * Created by Anna on 05.02.15.
 */
public class Booking {
    private long orderId;
    private long productId;
    private int quantity;
    private Date dateBooked;
    private Date dateDeliver;

    public Booking(long orderId, long productId, int quantity, Date dateBooked, Date dateDeliver) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.dateBooked = dateBooked;
        this.dateDeliver = dateDeliver;
    }

    public Date getDateDeliver() {
        return dateDeliver;
    }

    public void setDateDeliver(Date dateDeliver) {
        this.dateDeliver = dateDeliver;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateBooked() {
        return dateBooked;
    }

    public void setDateBooked(Date dateBooked) {
        this.dateBooked = dateBooked;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
