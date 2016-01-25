package com.my.jira.admin.data;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by tigran on 1/18/16.
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

    @Column(name = "fullName")
    private String fullName;


//    private ArrayList<Integer> tasksIds;

    public User(String userName, String password, String fullName) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
//        tasksIds = new ArrayList<Integer>();
    }

    public User() {

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /*@ElementCollection
    @CollectionTable(name = "taskIds", joinColumns=@JoinColumn(name="tasksIds"))
    @Column(name="tasksIds")
    public ArrayList<Integer> getTasksIds() {
        return tasksIds;
    }

    public void setTasksIds(ArrayList<Integer> tasksIds) {
        this.tasksIds = tasksIds;
    }*/

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + userId +
                ", \"userName\":\"" + userName + '\"' +
                ", \"password\":\"" + password + '\"' +
                ", \"fullName\":\"" + fullName + '\"' +
                '}';
    }
}
