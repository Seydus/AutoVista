package com.example.autovista.models.user;

import com.example.autovista.models.Entity;

public class User implements Entity {
    //attributes
    public enum USERTYPE {
        userRegular, userAdmin
    }

    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected USERTYPE userType;

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
