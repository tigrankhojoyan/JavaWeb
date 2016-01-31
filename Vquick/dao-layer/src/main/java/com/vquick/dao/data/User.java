package com.vquick.dao.data;

import javax.persistence.*;

/**
 * Created by tigran on 1/31/16.
 */
@Entity
@Table(name="USER")
public class User {

    @Id
    @Column(name="userId")
    @GeneratedValue
    private int userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "userData")
    private UserData userData;

    public User() {

    }

    public User(String userName, String password, UserData userData) {
        this.userName = userName;
        this.password = password;
        this.userData = userData;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"userId\":" + userId +
                ", \"userName\":\"" + userName + '\"' +
                ", \"password\":\"" + password + '\"' +
                ", \"userData\":" + userData +
                "}";
    }
}
