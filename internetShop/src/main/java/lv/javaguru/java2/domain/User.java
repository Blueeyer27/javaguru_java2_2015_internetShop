package lv.javaguru.java2.domain;

/**
 * Created by Anna on 05.02.15.
 */
public class User {
    private long id;
    private String name;
    private String surname;
    private String persCode;
    private String gender;
    private String phone;
    private String email;
    private String login;
    private String parole;
    private int level;

    public User() {
    }

    public User(long id, String name, String surname, String persCode,
                String gender, String phone, String email, String login,
                String parole, int level) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.persCode = persCode;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.login = login;
        this.parole = parole;
        this.level = level;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getParole() {
        return parole;
    }

    public void setParole(String parole) {
        this.parole = parole;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

