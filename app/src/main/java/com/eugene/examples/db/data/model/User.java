package com.eugene.examples.db.data.model;

/**
 * Created by eugene on 11/20/2017.
 */

public class User {
    private int id;
    private String fullName;
    private int age;
    private String city;

    public User() {

    }

    public User(String fullName, int age, String city) {
        this.fullName = fullName;
        this.age = age;
        this.city = city;
    }

    public User(int id, String fullName, int age, String city) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
