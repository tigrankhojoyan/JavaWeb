/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.rest;

/**
 *
 * @author tigran
 */
public class IndividualData {

    private String country;
    private String city;
    private String gender;
    private String email;
    private int age;
    private int id;

    public IndividualData() {

    }

    public IndividualData(String country, String city, String gender, String email, int age) {
        this.country = country;
        this.city = city;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String dataString = "{\"country\":\""
                + getCountry() + "\", \"city\": \""
                + getCity() + "\", \"gender\": \""
                + getGender() + "\", \"age\":"
                + getAge() + ", \"email\":\"" + getEmail() +
                "\", \"id\":"
                + getId() + "}";
        return dataString;
    }
}
