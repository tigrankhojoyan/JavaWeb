package com.vquick.dao.data;

import javax.persistence.*;

/**
 * Created by tigran on 1/31/16.
 */
@Entity
@Table(name = "USERDATA")
public class UserData {

    @Id
    @Column(name = "userDataId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userDataId;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private Integer age;

    public UserData() {

    }

    public UserData(String fullName, String address, Integer age) {
        this.fullName = fullName;
        this.address = address;
        this.age = age;
//        this.userDataId = 0;
    }

    public Integer getUserDataId() {
        return userDataId;
    }

    public void setUserDataId(Integer userDataId) {
        this.userDataId = userDataId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "{\"userDataId\":" + userDataId +
                ", \"fullName=\":\"" + fullName + '\"' +
                ", \"address\":\"" + address + '\"' +
                ", \"age\":" + age +
                '}';
    }
}
