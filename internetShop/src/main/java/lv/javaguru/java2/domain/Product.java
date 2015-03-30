package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

//    CREATE TABLE `products` (
//            `ProductID` int(11) NOT NULL AUTO_INCREMENT,
//    `Name` char(32) NOT NULL,
//    `Description` text NOT NULL,
//            `Price` decimal(20,6) unsigned NOT NULL,
//    `Picture` char(255) DEFAULT NULL,
//    PRIMARY KEY (`ProductID`)
//    ) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

    @Id
    @Column(name="ProductID",columnDefinition = "INT(11)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long productId;

    @Column(name = "Name", columnDefinition = "CHAR(32)")
    protected String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    protected String description;

    @Column(name = "Price", columnDefinition = "DECIMAL(20,6)")
    protected double price;

    @Column(name = "Picture", columnDefinition = "CHAR(255)")
    protected String imageURL;

    @OneToMany(mappedBy = "productID", fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    protected List<Comment> comments;

    public Product() {

    }

    public Product(String name, String description, float price) {
        this. name = name;
        this.description = description;
        this.price = price;
        this.imageURL = null;
    }

    public List<Comment> getComments() { return comments; }
    //public void setComments(List<Comment> value) { comments = value; }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String imageURL) { this.imageURL = imageURL; }
    public String getImage() { return imageURL; }
}
