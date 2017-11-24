package com.eugene.examples.db.data.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by eugene on 11/23/2017.
 */

public class RealmUser extends RealmObject {
    @PrimaryKey
    private String id;
    private String fullName;
    private int age;
    private String city;

    public RealmUser() {

    }

    public RealmUser(String fullName, int age, String city) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.age = age;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
