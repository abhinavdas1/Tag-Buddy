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

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public FriendSuggestion attemptLogin(@RequestBody User input)
    {
        this.userService.addUserData(input);

        return this.friendService.getFriends(input.getUserId());

    }
}
