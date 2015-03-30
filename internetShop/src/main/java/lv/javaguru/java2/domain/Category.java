package lv.javaguru.java2.domain;

import javax.persistence.*;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Created by Anna on 18.03.15.
 */
@Entity
@Table(name = "categories")
public class Category {

//    CREATE TABLE IF NOT EXISTS `java2_test`.`categories` (
//            `CatId` INT(11),
//    `CatName` CHAR(30),
//    PRIMARY KEY (`CatName`))
//    ENGINE = InnoDB
//    DEFAULT CHARACTER SET = utf8;

    @Column(name="CatId",columnDefinition = "int(11)")
    private long catId;

    @Column(name="CatName",columnDefinition = "CHAR(30)")
    @Id
    private String catName;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<NewItem> news;

    public Category() {
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<NewItem> getNews() {
        return news;
    }

    public void setNews(List<NewItem> news) {
        this.news = news;
    }
}
