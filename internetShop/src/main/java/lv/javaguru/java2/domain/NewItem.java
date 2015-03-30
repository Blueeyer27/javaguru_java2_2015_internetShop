package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "news")

public class NewItem {

//    CREATE TABLE IF NOT EXISTS `java2_test`.`news` (
//            `Num` INT(11) NOT NULL AUTO_INCREMENT,
//    `DateID` CHAR(30) NOT NULL,
//    `Title` CHAR(30) NOT NULL,
//    `Body` CHAR(80) NOT NULL,
//    `Likes` INT(11),
//    `CatName` CHAR(30),
//    PRIMARY KEY (`Num`))
//    ENGINE = InnoDB
//    DEFAULT CHARACTER SET = utf8;

    @Column(name="Num",columnDefinition = "int(11)")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long num;

    @Column(name="DateId",columnDefinition = "CHAR(30)")
    private String dateID;

    @Column(name="Title",columnDefinition = "CHAR(30)")
    private String title;

    @Column(name="Body",columnDefinition = "CHAR(80)")
    private String body;

    @Column(name="Likes",columnDefinition = "INT(11)")
    private int likes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CatName")
    private Category category;

    public NewItem(Category category, String dateID, String title, String body, int likes) {
        this.dateID = dateID;
        this.title = title;
        this.body = body;
        this.likes = likes;
        this.category = category;
    }

    public NewItem(String dateID, String title, String body, int likes) {
        this.dateID = dateID;
        this.title = title;
        this.body = body;
        this.likes = likes;
    }

    public NewItem() {
    }


    public String getDateID() {
        return dateID;
    }

    public void setDateID(String dateID) {
        this.dateID = dateID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public long getNum() { return num; }

    public void setNum(long num) { this.num = num; }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
