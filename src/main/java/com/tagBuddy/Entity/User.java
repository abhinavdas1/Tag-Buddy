package com.tagBuddy.Entity;

import java.util.ArrayList;

/**
 * Created by abhinavdas on 1/20/18.
 */
public class User {

    private String userId;
    private ArrayList<String> tags;

    public User(String userId, ArrayList<String> tags) {
        this.userId = userId;
        this.tags = tags;
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
