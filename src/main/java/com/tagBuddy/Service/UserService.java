package com.tagBuddy.Service;

import com.tagBuddy.Dao.UserDao;
import com.tagBuddy.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abhinavdas on 1/20/18.
 */

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public void addUserData(User input)
    {
        this.userDao.addUserData(input);
    }

    public boolean checkIfExisting(String userId) {

        if(this.userDao.exists(userId))
        {
            return true;
        }

        return false;
    }

    public boolean compareRecord(User user) {

        Map<String, Double> tagCountOld = this.userDao.getTagCount(user.getUserId());

        Map<String, Double> tagCountNew = new HashMap<String, Double>();

        for(int i = 0; i < user.getTags().size(); i++)
        {
            Double count  = tagCountNew.getOrDefault(user.getTags().get(i), 0.0);
            tagCountNew.put(user.getTags().get(i), ++count);
        }

        for(Map.Entry<String, Double> e : tagCountNew.entrySet())
        {
            tagCountNew.put(e.getKey(), e.getValue() / user.getTags().size());
        }

        for(Map.Entry<String, Double> e : tagCountNew.entrySet())
        {
            if(e.getValue() != tagCountOld.get(e.getKey()))
            {
                return false;
            }
        }

        return true;

    }

    public void updateUserRecord(User user) {

        this.userDao.removeUser(user.getUserId());

        this.userDao.removeUserInstances(user.getUserId());

        this.userDao.addUserData(user);

    }
}