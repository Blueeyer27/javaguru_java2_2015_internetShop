package lv.javaguru.java2.domain;

/**
 * Created by Anton on 2015.03.01..
 */
public class Comment {
    private Long id, userID, productID;
    private String comment;

    public Comment(Long userID, Long productID, String comment){
        this.comment = comment;
        this.userID = userID;
        this.productID = productID;
    }

    public Long getUserID() { return userID; }
    public void setUserID(Long value) { userID = value; }

    public Long getId() { return id; }
    public void setId(Long value) { id = value; }

    public Long getProductID() { return productID; }
    public void setProductID(Long value) { productID = value; }

    public String getComment() { return comment; }
    public void setComment(String value) { comment = value; }
}
