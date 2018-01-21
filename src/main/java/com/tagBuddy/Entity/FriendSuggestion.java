package com.tagBuddy.Entity;

import java.util.List;
import java.util.Map;

/**
 * Created by abhinavdas on 1/20/18.
 */
public class FriendSuggestion {

    private Map<String, Double> matchCounts;
    private Map<String, java.util.List<String>> matchedTags;
    private Map<String, String> userNames;
    private Map<String, String> userPicture;

    public Map<String, String> getUserNames() {
        return this.userNames;
    }

    public void setUserNames(Map<String, String> userNames) {
        this.userNames = userNames;
    }

    public Map<String, String> getUserPicture() {
        return this.userPicture;
    }

    public void setUserPicture(Map<String, String> userPicture) {
        this.userPicture = userPicture;
    }

    public FriendSuggestion(Map<String, Double> matchCounts, Map<String, List<String>> matchedTags, Map<String, String> userNames, Map<String, String> userPicture) {
        this.matchCounts = matchCounts;
        this.matchedTags = matchedTags;
        this.userNames = userNames;
        this.userPicture = userPicture;
    }

    public Map<String, Double> getMatchCounts() {
        return this.matchCounts;
    }

    public void setMatchCounts(Map<String, Double> matchCounts) {
        this.matchCounts = matchCounts;
    }

    public Map<String, List<String>> getMatchedTags() {
        return this.matchedTags;
    }

    public void setMatchedTags(Map<String, List<String>> matchedTags) {
        this.matchedTags = matchedTags;
    }

    public FriendSuggestion(){

    }
}
