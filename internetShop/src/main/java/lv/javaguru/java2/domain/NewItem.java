package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Anna on 27.02.15.
 */

public class NewItem {

    private String dateID;
    private String title;
    private String body;
    private int likes;

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
}
