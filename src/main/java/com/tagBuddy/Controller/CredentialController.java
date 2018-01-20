package com.tagBuddy.Controller;

import com.tagBuddy.Dao.CredentialDao;
import com.tagBuddy.Entity.Credential;
import com.tagBuddy.Service.CredentialService;
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
@RequestMapping("/login")
public class CredentialController {

    @Autowired
    CredentialService credentialService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void attemptLogin(@RequestBody Credential input)
    {
        this.credentialService.attemptLogin(input);
    }
}
