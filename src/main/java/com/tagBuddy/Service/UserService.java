package com.tagBuddy.Service;

import com.tagBuddy.Dao.UserDao;
import com.tagBuddy.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}