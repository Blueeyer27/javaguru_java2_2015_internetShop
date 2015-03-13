package lv.javaguru.java2.domain;

import lv.javaguru.java2.database.CommentDAO;

import javax.persistence.*;

/**
 * Created by Anton on 2015.03.01..
 */

@Entity
@Table(name = "comments")
public class Comment {
    /*
   `ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Comment` CHAR(255) NULL DEFAULT NULL,
  `UserID` INT(11) NOT NULL,
  `ProductID` INT(11) NOT NULL
    */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID",columnDefinition = "INT(11)")
    private Long id;

//    @Column(name="UserID",columnDefinition = "INT(11)")
//    private Long userID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ProductID")
    private Product product;

    //@Column(name="ProductID",columnDefinition = "INT(11)")
    //private Long productID

    @Column(name = "Comment", columnDefinition = "CHAR(255)")
    private String comment;

    //private String username;

    public Comment() {

    }

    public Comment(User user, Product product, String comment){
        this.comment = comment;
        //this.userID = userID;
        //this.productID = productID;
        this.user = user;
        this.product = product;
    }

    public Long getUserID() { return user.getId(); }
    //public void setUserID(Long value) { userID = value; }

    public Long getId() { return id; }
    public void setId(Long value) { id = value; }

//    public Long getProductID() { return productID; }
//    public void setProductID(Long value) { productID = value; }

    public String getComment() { return comment; }
    public void setComment(String value) { comment = value; }

    public String getUsername() { return user.getLogin(); }
//    public void setUsername(String value) { value = value; }

    public Product getProduct() { return product; }
    public void setProduct(Product value) { product = value; }
}
