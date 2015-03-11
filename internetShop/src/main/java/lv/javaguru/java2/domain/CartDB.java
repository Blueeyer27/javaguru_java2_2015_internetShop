package lv.javaguru.java2.domain;

import javax.persistence.*;

/**
 * Created by Anton on 2015.03.04..
 */

@Entity
@Table(name = "carts")
public class CartDB {

    /*
    `ProductID` INT(11) NOT NULL,
    `UserID` INT(11) NOT NULL,
    `Count` INT UNSIGNED NULL DEFAULT 0,
    `IsOrdered` TINYINT(1) NULL DEFAULT 0,
    */

    @Column(name = "ID",columnDefinition = "INT(11)")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;

    //@Column(name="ProductID",columnDefinition = "INT(11)")
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ProductID")
    private Product productID;

    @Column(name="UserID",columnDefinition = "INT(11)")
    private long userID;

    @Column(name="Count",columnDefinition = "INT UNSIGNED")
    private Integer count;

    @Column(name="IsOrdered",columnDefinition = "BIT")
    private boolean isOrdered;

    //@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    //@JoinTable(name = "products")
    //private Product product;

    public  CartDB() {

    }

//    public CartDB(Long productID, Long userID,
//                  Integer count, boolean isOrdered) {
public CartDB(Product productID, Long userID,
              Integer count, boolean isOrdered) {
        this.productID = productID;
        this.userID = userID;
        this.count = count;
        this.isOrdered = isOrdered;
    }

    public Product getProductID() { return productID; }
    public void setProductID(Product value) { productID = value; }

    public long getID() { return ID; }
    public void setID(long value) { ID = value; }

    public long getUserID() { return userID; }
    public void setUserID(long value) { this.userID = value; }

//    public long getProductId() { return productID.getProductId(); }
//    public void setProductId(long value) { this.productID.setProductId(value); }

    public Integer getCount() { return count; }
    public void setCount(int value) { count = value; }

    public boolean getIsOrdered() { return isOrdered; }
    public void setIsOrdered(boolean value) { isOrdered = value; }
}
