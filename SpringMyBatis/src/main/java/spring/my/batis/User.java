/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.my.batis;

/**
 *
 * @author tigran
 */
public class User {

    private Integer userId;
    private String emailId;
    private String password;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "User [userId=" + userId + ", emailId=" + emailId
                + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + "]";
    }

    public User() {

    }

    public User(String emailId, String password,
            String firstName, String lastName) {
        this.emailId = emailId;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
