package com.tagBuddy.Entity;

import java.util.List;
import java.util.Map;

/**
 * Created by abhinavdas on 1/20/18.
 */
public class FriendSuggestion {

    private Map<String, Integer> matchCounts;
    private Map<String, java.util.List<String>> matchedTags;

    public FriendSuggestion(Map<String, Integer> matchCounts, Map<String, List<String>> matchedTags) {
        this.matchCounts = matchCounts;
        this.matchedTags = matchedTags;
    }

    public Map<String, Integer> getMatchCounts() {
        return this.matchCounts;
    }

    public void setMatchCounts(Map<String, Integer> matchCounts) {
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
