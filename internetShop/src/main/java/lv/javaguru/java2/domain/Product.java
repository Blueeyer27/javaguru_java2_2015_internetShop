package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    /*
    CREATE TABLE IF NOT EXISTS `java2_test`.`products` (
      `ProductID` INT(11) NOT NULL AUTO_INCREMENT,
      `Name` CHAR(32) NOT NULL,
      `Description` TEXT NOT NULL,
      `Price` DECIMAL(20,6) UNSIGNED NOT NULL,
      `Picture` CHAR(255) NULL DEFAULT NULL,
      PRIMARY KEY (`ProductID`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
     */

    @Column(name="ProductID",columnDefinition = "INT(11)")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long productId;

    @Column(name = "Name", columnDefinition = "CHAR(32)")
    protected String name;

    @Column(name = "Description", columnDefinition = "TEXT")
    protected String description;

    @Column(name = "Price", columnDefinition = "DECIMAL(20,6)")
    protected float price;

    @Column(name = "Picture", columnDefinition = "CHAR(255)")
    protected String imageURL;

//    @OneToMany
//    protected List<Comment> comments;

    public Product() { }

    public Product(String name, String description, float price) {
        this. name = name;
        this.description = description;
        this.price = price;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImage(String imageURL) { this.imageURL = imageURL; }
    public String getImage() { return imageURL; }
}
