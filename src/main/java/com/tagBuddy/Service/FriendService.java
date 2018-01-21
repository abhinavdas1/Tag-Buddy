package com.tagBuddy.Service;

//import com.sun.tools.javac.util.List;
import com.tagBuddy.Dao.UserDao;
import com.tagBuddy.Entity.FriendSuggestion;
import com.tagBuddy.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map;

/**
 * Created by abhinavdas on 1/20/18.
 */
@Service
public class FriendService {
    @Autowired
    UserDao userDao;

    public FriendSuggestion getFriends(String id)
    {
        Map<String, Double> tagCounts = userDao.getTagCount(id);
        Map<String, java.util.List<String>> tagUsers = userDao.getUserTags();

        Map<String, Double> matchCounts = new HashMap<String, Double>();
        Map<String, java.util.List<String>> matchedTags = new HashMap<String, java.util.List<String>>();

        Map<String, String> userNames = this.userDao.getNames();

        Map<String, String> userPicture = this.userDao.getPictures();


        for(Map.Entry<String, Double> e : tagCounts.entrySet())
        {
            for(String temp : tagUsers.get(e.getKey()))
            {
                if(temp.equals(id))
                    continue;
                double count = matchCounts.getOrDefault(temp, 0.0);
                Map<String, Double> tagCountsUser = userDao.getTagCount(temp);
                matchCounts.put(temp, count + Math.min(tagCounts.get(e.getKey()), tagCountsUser.get(e.getKey())));

                java.util.List<String> tagList = matchedTags.getOrDefault(temp, new ArrayList<String>());
                tagList.add(e.getKey());
                matchedTags.put(temp, tagList);
            }
        }



        FriendSuggestion friendSuggestion = new FriendSuggestion(matchCounts, matchedTags, userNames, userPicture);

        return friendSuggestion;
    }
}
