package lv.javaguru.java2.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name="UserId",columnDefinition = "int(11)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Login", columnDefinition = "CHAR(16)")
    private String username;

    @Column(name = "Password", columnDefinition = "CHAR(80)")
    private String password;

    @Column(name = "Access_Level", columnDefinition = "INT(3)")
    private int access_level;

    @Column(name = "Name", columnDefinition = "CHAR(32)")
    private String name;

    @Column(name = "Surname", columnDefinition = "CHAR(32)")
    private String surname;

    @Column(name = "Gender", columnDefinition = "CHAR(6)")
    private String gender;

    @Column(name = "Phone", columnDefinition = "CHAR(15)")
    private String phone;

    @Column(name = "Email", columnDefinition = "CHAR(30)")
    private String email;

    @Column(name = "Photo", columnDefinition = "CHAR(255)")
    private String photoURL;

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

    public String getPhotoURL() { return photoURL; }
    public void setPhotoURL(String value) { photoURL = value; }


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
