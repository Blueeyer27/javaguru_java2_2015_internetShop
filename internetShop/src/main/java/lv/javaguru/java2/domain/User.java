package lv.javaguru.java2.domain;

/**
 * Created by Anna on 05.02.15.
 */
public class User {
    private long id;

    private String username;
    private String password;
    private int access_level;

    private String name;
    private String surname;
    private String gender;
    private String phone;
    private String email;


    public User() {
    }

    public User(String name, String surname,
                String gender, String phone, String email, String login,
                String password, int access_level) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.username = login;
        this.password = password;
        this.access_level = access_level;
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
        return username;
    }

    public void setLogin(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccessLevel() {
        return access_level;
    }

    public void setAccessLevel(int access_level) {
        this.access_level = access_level;
    }
}

