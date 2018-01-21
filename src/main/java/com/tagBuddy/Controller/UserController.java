package com.tagBuddy.Controller;


import com.tagBuddy.Entity.FriendSuggestion;
import com.tagBuddy.Entity.User;
import com.tagBuddy.Service.FriendService;
import com.tagBuddy.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by abhinavdas on 1/20/18.
 */
@RestController
@RequestMapping("/api/v1/user/add")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @RequestMapping(method = RequestMethod.POST, consumes = "text/plain")
    public FriendSuggestion attemptLogin(@RequestBody String input)
    {
        System.out.println("here");
        System.out.println(input);
        String[] params = input.split("&");
        User user = new User();

        String listOfTags = params[1].split("=")[1];
        ArrayList<String> tags = new ArrayList<String>(Arrays.asList(listOfTags.split(",")));

        user.setUserId(params[0].split("=")[1]);
        user.setTags(tags);

        user.setName(params[2].split("=")[1]);
        user.setPicture(params[3].split("=")[1]);

        System.out.println(user.getName() + " " + user.getPicture());

        if(!this.userService.checkIfExisting(user.getUserId()))
        {
            this.userService.addUserData(user);
        }
        else
        {
            if(!this.userService.compareRecord(user))
            {
                this.userService.updateUserRecord(user);
            }

        }

        return this.friendService.getFriends(user.getUserId());


    }
}
