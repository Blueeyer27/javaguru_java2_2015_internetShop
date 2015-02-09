package lv.javaguru.java2.domain;

/**
 * Created by Anna on 05.02.15.
 */
public class Client {
    private long id;
    private String name;
    private String surname;
    private String persCode;
    private String gender;

    public Client() {
    }

    public Client(long id, String name, String surname, String persCode, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.persCode = persCode;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersCode() {
        return persCode;
    }

    public void setPersCode(String persCode) {
        this.persCode = persCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

