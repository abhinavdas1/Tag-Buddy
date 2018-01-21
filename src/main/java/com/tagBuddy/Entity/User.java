package com.tagBuddy.Entity;

import java.util.ArrayList;

/**
 * Created by abhinavdas on 1/20/18.
 */
public class User {

    private String userId;
    private ArrayList<String> tags;
    private String name;
    private String picture;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User(String userId, ArrayList<String> tags, String name, String picture) {
        this.userId = userId;
        this.tags = tags;
        this.name = name;
        this.picture = picture;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public User(){

    }


}
