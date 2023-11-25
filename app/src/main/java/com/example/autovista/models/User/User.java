package com.example.autovista.models.User;

import Entity.Entity;

public class User implements Entity{
    //attributes
    public enum USERTYPE {
        userRegular, userAdmin
    }

    protected int id;
    protected String username;
    protected String email;
    protected String password;
    protected USERTYPE userType;

    //constructor
    public User(int id, String username, String email, String password, USERTYPE userType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public USERTYPE getUserType() {
        return userType;
    }
    public void setUserType(USERTYPE userType) {
        this.userType = userType;
    }
}
