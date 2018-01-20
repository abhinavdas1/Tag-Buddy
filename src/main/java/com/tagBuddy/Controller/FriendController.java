package com.tagBuddy.Controller;

import com.sun.tools.javac.util.List;
import com.tagBuddy.Entity.FriendSuggestion;
import com.tagBuddy.Entity.User;
import com.tagBuddy.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by abhinavdas on 1/20/18.
 */
@RestController
@RequestMapping("/api/v1/friend/findFriends")
public class FriendController {
    @Autowired
    FriendService friendService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public FriendSuggestion attemptLogin(@PathVariable("id") String id)
    {
        return this.friendService.getFriends(id);
    }
}
