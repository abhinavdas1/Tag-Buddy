package com.tagBuddy.Entity;

/**
 * Created by abhinavdas on 1/20/18.
 */
public class Credential {

    private String username;

    public Credential(){

    }

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
