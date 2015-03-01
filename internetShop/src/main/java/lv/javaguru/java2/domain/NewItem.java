package lv.javaguru.java2.domain;

/**
 * Created by Anna on 27.02.15.
 */
public class NewItem {
    private String dateID;
    private String title;
    private String body;

    public NewItem(String dateID, String title, String body) {
        this.dateID = dateID;
        this.title = title;
        this.body = body;
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
}
